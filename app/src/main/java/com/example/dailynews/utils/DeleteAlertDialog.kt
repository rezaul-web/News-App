package com.example.dailynews.utils
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeleteAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String = "Confirm Deletion",
    dialogText: String = "Are you sure you want to delete this Article?",
    titleColor: Color = Color.Black,
    textColor: Color =  Color.Black,
    confirmButtonColor: Color = Color.Gray,
    dismissButtonColor: Color = Color.Red,
    backgroundColor: Color = Color.White
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest()
        },
        title = {
            Text(
                text = dialogTitle,
                color = titleColor,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = dialogText,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = confirmButtonColor)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = dismissButtonColor)
            ) {
                Text("Dismiss")
            }
        },
        containerColor = backgroundColor // Background color for the dialog
    )
}
