package com.project.onycheck.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.project.onycheck.R

val Inter = FontFamily(
    // Normal styles
    Font(R.font.inter_thin, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.inter_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.inter_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.inter_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.inter_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.inter_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.inter_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.inter_black, FontWeight.Black, FontStyle.Normal),

    // Italic styles
    Font(R.font.inter_italic_thin, weight = FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.inter_italic_extralight, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.inter_italic_light, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.inter_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.inter_italic_medium, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.inter_italic_semibold, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.inter_italic_bold, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.inter_italic_extrabold, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.inter_italic_black, weight = FontWeight.Black, style = FontStyle.Italic)
)