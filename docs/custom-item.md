# カスタムアイテム

カスタムアイテムはバニラに存在しないアイテムを実装する方法です。

## 1. カスタムアイテムタイプの追加

### 1-1. 定義オブジェクトの作成

`net.azisaba.vanilife.item.CustomItemType` を実装して定義オブジェクトを作成します。

機能的なカスタムアイテムを作成する方法として、`CustomItemType` を継承したいくつかのインターフェースが提供されます。

`Consumable`, `Fish`, `Food`, `Fruit`, `Priced`, `Seasonal`, `Vegetable`

> [!NOTE]
> カスタムアイテムタイプの定義クラスは `core` モジュールの `net.azisaba.vanilife.item` パッケージに格納します。

> [!CAUTION]
> 定義オブジェクト内のプロパティで他のカスタムアイテムタイプの定義オブジェクトを参照しないでください。

```kotlin
object AppleJam: CustomItemType {
    // 一意のキーです
    override val key: Key = Key.key(Vanilife.PLUGIN_ID, "apple_jam")

    // ベースにするアイテムタイプを指定します 基本的には棒を使用します
    override val itemType: ItemType = ItemType.STICK

    // 任意: アイテムモデルを指定します
    override val itemModel: Key = Key.key(Vanilife.PLUGIN_ID, "apple_jam")

    // 表示名を指定します 基本的には生のテキストではなく、翻訳テキストを使用してください
    override val displayName: Component = Component.translatable("item.vanilife.apple_jam")
    
    // 任意: 説明欄を指定します 基本的には生のテキストではなく、翻訳テキストを使用してください
    override val lore: List<Component> = listOf(Component.translatable("item.vanilife.apple_jam.description"))
    
    // アイテムグループを指定します これはアイテムリストのタブを意味します
    override val group: ItemGroup = ItemGroups.FOODSTUFF
    
    // 任意: アイテムの希少度を指定します Mojang のガイドラインに基づいてください (https://ja.minecraft.wiki/w/希少度#段階)
    override val rarity: ItemRarity = ItemRarity.COMMON
    
    // 任意: 最大スタック数を指定します 1~99 で指定する必要があります
    override val maxStackSize: Int = 64
    
    // 任意: エンチャントのオーラを持つかを指定します これは実際には無限のエンチャントを付与するオプションであるため、true にする場合は副作用に注意してください
    override val enchantmentAura: Boolean = false
    
    fun onInHand(player: Player) {
        // 任意: メインハンドまたはオフハンドにある場合に毎ティック呼び出されます
    }
    
    fun onInMainHand(player: Player) {
        // 任意: メインハンドにある場合に毎ティック呼び出されます
    }
    
    fun onInOffHand(player: Player) {
        // 任意: オフハンドにある場合に毎ティック呼び出されます
    }
    
    fun use(player: Player, action: Action, block: Block?, blockFace: BlockFace) {
        // 任意: インタラクトされたときに呼び出されます
    }
}
```

### 1-2. レジストリへの登録

`net.azisaba.vanilife.registry.CustomItemTypes` レジストリに定義オブジェクトを登録します。

> [!NOTE]
> レジストリ内のプロパティはアルファベット順に並べてください

```kotlin
object CustomItemTypes: KeyedRegistry<CustomItemType>() {
    val APPLE_JAM = register(AppleJam)
    // 登録が続きます…
}
```

## 2. カスタムアイテムの作成

カスタムアイテムタイプを実際に ItemStack としてアイテム化しましょう。

> [!TIP]
> `createItemStack` メソッドの引数に個数を渡すことも可能です。
> これはオプションで、指定しない場合には1が使用されます。

```kotlin
val itemStack = CustomItemTypes.APPLE_JAM.createItemStack()
```

## 3. カスタムアイテムの操作

アイテムスタックからカスタムアイテムタイプを取得するには、`customItemType` プロパティを使用します。
カスタムアイテムではない場合、`null` が返されます。

カスタムアイテムかを確認するには、`hasCustomItemType()` を呼び出します。

> [!TIP]
> これらのプロパティ・メソッドは `net.azisaba.vanilife.extension.ItemExtension` から
> 拡張プロパティ・メソッドとして提供されます。

```kotlin
val customItemType = itemStack.customItemType

if (itemStack.hasCustomItemType()) {
    val customItemType2 = itemStack.customItemType!!
}
```