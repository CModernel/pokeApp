package com.pedrogomez.pokeapp.main_menu.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pedrogomez.pokeapp.core.ui.theme.pokeLabelLarge
import com.pedrogomez.pokeapp.core.ui.theme.pokeLabelMedium
import com.pedrogomez.pokeapp.core.ui.theme.pokeLabelSmall

@Composable
fun ListRow(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    iconContent: @Composable (() -> Unit)? = null,
    displayTag: @Composable (() -> Unit)? = null,
    rowHeight: Dp = 60.dp,
) {
    Row(
        modifier = modifier
            .height(rowHeight)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable { onClick?.let { it() } },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconContent != null) {
            iconContent()
        }

        Column(
            modifier = Modifier
                .padding(
                    start = if (iconContent == null) {
                        12.dp
                    } else {
                        0.dp
                    },
                ),
        ) {
            Spacer(modifier = Modifier.weight(1.0f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val weightModifier = Modifier.takeIf { displayTag == null }?.weight(1.0f) ?: Modifier
                Column(modifier = weightModifier) {
                    Text(
                        text = AnnotatedString(title),
                        style = MaterialTheme.typography.pokeLabelLarge,
                    )
                    if (subtitle != null) {
                        Text(
                            modifier = Modifier.padding(top = 2.dp),
                            text = subtitle,
                            style = MaterialTheme.typography.pokeLabelSmall,
                            color = Color.Gray,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Composable
fun ListRowIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    marginPadding: Dp = 16.dp,
    iconDrawSize: Dp = 20.dp,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        colorFilter = colorFilter,
        modifier = modifier
            .padding(horizontal = marginPadding)
            .size(iconDrawSize),
    )
}

@Preview(showBackground = true)
@Composable
private fun ListCardPreview(){
    Column() {
        ListRow(
            title = "Title",
            subtitle = "Subtitle",
        )
        ListRow(
            title = "Title",
        )
    }
}