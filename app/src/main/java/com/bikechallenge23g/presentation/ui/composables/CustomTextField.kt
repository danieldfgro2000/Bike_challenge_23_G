package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bikechallenge23g.data.model.enums.DistanceUnits
import com.bikechallenge23g.theme.AppCappuccino

@Composable
fun CustomTextField(
    value: String,
    error: String?,
    modifier: Modifier,
    displayUnit: Boolean = false,
    unit: DistanceUnits = DistanceUnits.KM,
    onValueChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .imePadding()
                .onFocusChanged { focusState -> isFocused = focusState.isFocused },
            border = BorderStroke(
                width = 1.dp,
                color = when {
                    error != null && !isFocused -> Color.Red
                    else -> AppCappuccino
                }
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    interactionSource = interactionSource,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusRequester.freeFocus() }),
                    textStyle = MaterialTheme.typography.labelMedium
                        .plus(
                            LocalTextStyle.current.copy(
                                color = AppCappuccino,
                                textIndent = TextIndent(firstLine = 20.sp)
                            )
                        ),
                    cursorBrush = SolidColor(AppCappuccino)
                )
                if (displayUnit) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextLabel(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        inputText = unit.name
                    )
                }
            }

        }
        if (error != null && !isFocused) {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = error,
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Red)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTextFieldCustomWithoutError() {
    CustomTextField(value = "Nuke", error = null, modifier = Modifier) {}
}

@Preview
@Composable
private fun PreviewTextFieldCustomWithError() {
    CustomTextField(value = "Nuke", error = "Required field", modifier = Modifier) {}
}
