package net.azisaba.vanilife.gui

import com.tksimeji.gonunne.item.CustomItemType
import com.tksimeji.gonunne.item.createItemStack
import com.tksimeji.gonunne.item.group.ItemGroup
import com.tksimeji.kunectron.ChestGui
import com.tksimeji.kunectron.Kunectron
import com.tksimeji.kunectron.element.Element
import com.tksimeji.kunectron.event.ChestGuiEvents
import com.tksimeji.kunectron.event.GuiHandler
import com.tksimeji.kunectron.hooks.ChestGuiHooks
import com.tksimeji.kunectron.policy.Policy
import net.azisaba.vanilife.registry.ItemGroups
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType
import kotlin.math.max
import kotlin.math.min

@ChestGui
class ItemListGui(@ChestGui.Player private val player: Player, private val group: ItemGroup = ItemGroups.ALL, private val page: Int = 0, private val query: String? = null): ChestGuiHooks, Searchable {
    companion object {
        private val GROUP_INDEXES = (1..7).toList()
        private val ITEM_INDEXES = listOf(19..25, 28..34, 37..43).flatten()

        private val CHUNKED_GROUPS = ItemGroups.chunked(GROUP_INDEXES.size)
    }

    private val groupChunk = CHUNKED_GROUPS.first { it.contains(group) }

    private val customItemTypes = search(group, query)

    @ChestGui.Title
    private val title = if (query == null) {
        Component.translatable("gui.itemList")
            .append(Component.text(":"))
            .append(group.title)
            .appendSpace()
            .append(Component.text("(${page + 1}/${(customItemTypes.size + ITEM_INDEXES.size - 1) / ITEM_INDEXES.size})"))
    } else {
        Component.translatable("gui.itemList.searchResult")
            .append(Component.text(":"))
            .appendSpace()
            .append(Component.text(query))
    }

    @ChestGui.Element(index = [9])
    private val tabPrevious = Element.playerHead()
        .url("http://textures.minecraft.net/texture/76ebaa41d1d405eb6b60845bb9ac724af70e85eac8a96a5544b9e23ad6c96c62")
        .title(Component.translatable("gui.previousTabPage"))
        .handler { ->
            val currentChunk = CHUNKED_GROUPS.indexOfFirst { it.contains(group) }

            if (currentChunk < 1) {
                return@handler
            }

            val previousChunk = CHUNKED_GROUPS[currentChunk - 1]
            Kunectron.create(ItemListGui(player, previousChunk.first(), query = query))
        }

    @ChestGui.Element(index = [17])
    private val tabNext = Element.playerHead()
        .url("http://textures.minecraft.net/texture/8399e5da82ef7765fd5e472f3147ed118d981887730ea7bb80d7a1bed98d5ba")
        .title(Component.translatable("gui.nextTabPage"))
        .handler { ->
            val currentChunk = CHUNKED_GROUPS.indexOfFirst { it.contains(group) }

            if (currentChunk >= CHUNKED_GROUPS.size - 1) {
                return@handler
            }

            val nextChunk = CHUNKED_GROUPS[currentChunk + 1]
            Kunectron.create(ItemListGui(player, nextChunk.first(), query = query))
        }

    @ChestGui.Element(index = [45])
    private val previous = Element.item(ItemType.ARROW)
        .title(Component.translatable("gui.previousPage").color(NamedTextColor.GREEN))
        .handler { -> Kunectron.create(ItemListGui(player, group, max(page - 1, 0))) }

    @ChestGui.Element(index = [48])
    private val search = Element.item(ItemType.COMPASS)
        .title(Component.translatable("gui.itemList.search"))
        .handler { -> Kunectron.create(SearchGui(player, this)) }

    @ChestGui.Element(index = [49])
    private val close = Element.item(ItemType.BARRIER)
        .title(Component.translatable("gui.close"))
        .handler { -> useClose() }

    @ChestGui.Element(index = [53])
    private val next = Element.item(ItemType.ARROW)
        .title(Component.translatable("gui.nextPage").color(NamedTextColor.GREEN))
        .handler { -> Kunectron.create(ItemListGui(player, group, min(page + 1, ((customItemTypes.size + ITEM_INDEXES.size - 1) / ITEM_INDEXES.size) - 1))) }

    @ChestGui.PlayerDefaultPolicy
    private val playerDefaultPolicy = Policy.itemSlot(true)

    override fun search(query: String?) {
        Kunectron.create(ItemListGui(player, group, 0, query))
    }

    private fun search(group: ItemGroup, query: String?): List<CustomItemType> {
        return group.filter { query == null || it.key.asString().contains(query, ignoreCase = true) }.toList()
    }

    @GuiHandler
    fun onInit(event: ChestGuiEvents.InitEvent) {
        for (index in ITEM_INDEXES) {
            useElement(index, Element.item(ItemType.GRAY_DYE).handler { -> player.setItemOnCursor(null) })
        }

        for ((index, group) in groupChunk.withIndex()) {
            if (GROUP_INDEXES.size <= index) {
                break
            }

            val index2 = GROUP_INDEXES[index]

            useElement(index2, group.icon
                .title(group.title.colorIfAbsent(if (group == this.group) NamedTextColor.WHITE else NamedTextColor.GRAY).decorationIfAbsent(TextDecoration.BOLD, TextDecoration.State.TRUE))
                .lore(Component.translatable("gui.itemList.tab.description", Component.text(search(group, query).size)).color(NamedTextColor.DARK_GRAY))
                .handler { -> Kunectron.create(ItemListGui(player, group, query = query)) })

            useElement(index2 + 9, Element.item(if (group == this.group) ItemType.GREEN_STAINED_GLASS_PANE else ItemType.GRAY_STAINED_GLASS_PANE))
        }

        for ((index, customItemType) in customItemTypes.subList(page * ITEM_INDEXES.size, min((page + 1) * ITEM_INDEXES.size, customItemTypes.size)).withIndex()) {
            val index2 = ITEM_INDEXES[index]
            val itemStack = customItemType.createItemStack()
            val element = Element.item(itemStack)
                .lore((itemStack.itemMeta.lore() ?: mutableListOf()).apply {
                    if (isNotEmpty()) {
                        add(Component.empty())
                    }
                    add(Component.text(customItemType.key.asString()).color(NamedTextColor.DARK_GRAY))
                })
                .handler { clickEvent ->
                    val result = customItemType.createItemStack()
                    val itemOnCursor = player.itemOnCursor

                    if (clickEvent.isShiftClick) {
                        result.amount = result.maxStackSize
                        player.setItemOnCursor(result)
                    } else if (itemOnCursor.isSimilar(result)) {
                        if (itemOnCursor.amount < itemOnCursor.maxStackSize) {
                            player.setItemOnCursor(itemOnCursor.apply { amount++ })
                        }
                    } else {
                        player.setItemOnCursor(result)
                    }
                }
            useElement(index2, element)
        }
    }
}