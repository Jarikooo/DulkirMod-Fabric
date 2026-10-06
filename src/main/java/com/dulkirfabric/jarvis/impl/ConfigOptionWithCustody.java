package com.dulkirfabric.jarvis.impl;

import com.dulkirfabric.jarvis.api.JarvisConfigOption;
import com.dulkirfabric.jarvis.api.JarvisPlugin;

public record ConfigOptionWithCustody(JarvisPlugin plugin, JarvisConfigOption option) {
}
