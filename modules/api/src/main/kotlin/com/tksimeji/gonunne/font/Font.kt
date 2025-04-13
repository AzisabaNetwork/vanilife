package com.tksimeji.gonunne.font

import com.tksimeji.gonunne.key.Keyed
import com.tksimeji.gonunne.registry.Registry

interface Font: Registry<String, Char>, Keyed {
}