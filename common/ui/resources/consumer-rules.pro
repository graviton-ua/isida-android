# This rule is required for the Navigation2 library to function correctly.
# The 'Country' enum is used as a key in a Map<Country, Double> on the SelectShippingScreen.
# If this enum is obfuscated, the library fails to build its navigation graph, causing a crash.
## Example of crash:
# Route com.whoppah.ui.create.shipping.SelectShippingScreen could not find any NavType for argument customShippingPrices of type kotlin.collections.LinkedHashMap? -
# typeMap received was {com.dixa.messenger.ofs.y2j.b?=nav_type, kotlin.collections.Map<com.dixa.messenger.ofs.zp4, kotlin.Double>?=nav_type}
-keep class com.whoppah.common.resources.Country { *; }