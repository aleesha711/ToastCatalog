package com.sumup.challenge.toastcatalog.toasts.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumup.challenge.toastcatalog.designsystem.CircleWithText
import com.sumup.challenge.toastcatalog.toasts.presentation.model.ToastUiModel

/**
 * UI for Toast Catalog item to be shown in the listing
 * @param toast ui model, the data we want to show
 *
 */
@Composable
fun ItemToast(
    toast: ToastUiModel,
) {
    // card layout configuration
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {

            CircleWithText(text = toast.id.toString(), color = Color.Black)
            // column to show information in vertical orientation
            Column(
                modifier = Modifier.
                padding(12.dp),
            ) {
                // text field to show title
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = toast.name,
                    style = MaterialTheme.typography.titleSmall,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                )  {

                    // text field to show price

                    Text(
                        modifier = Modifier.wrapContentSize(),
                        text = toast.price,
                        style = MaterialTheme.typography.bodySmall,
                    )

                    // text field to show sold out date

                    toast.soldAt?.let { soldAt ->
                        Spacer(modifier = Modifier.padding(vertical = 4.dp))
                        Text(
                            text = soldAt,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }

    }
}

/**
 * preview the item UI
 */
@Preview
@Composable
fun PreviewItemToast() {
    ItemToast(
        ToastUiModel(
            id = 1,
            name = "Toast Name",
            price = "Price in €",
            soldAt = "28/11/20 03:14 PM"
        )
    )
}
