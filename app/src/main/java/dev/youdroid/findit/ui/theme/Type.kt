package dev.youdroid.findit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import dev.youdroid.findit.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val cairo = GoogleFont("Cairo")
val fontFamily = FontFamily(
    Font(googleFont = cairo, fontProvider = provider)
)

// Set of Material typography styles to start with
@Composable
fun getTypo(): Typography {
    return Typography(
        displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = fontFamily),
    )
}