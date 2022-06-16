package artevak.com.traincompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoginActivity : ComponentActivity() {

    val itemsModel = List(1) {
        "$it"
    }.toMutableStateList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLogin by remember { mutableStateOf(false) }
            var strName by remember { mutableStateOf("") }

            val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
            val coroutineScope = rememberCoroutineScope()

            MaterialTheme {
                Scaffold(
                    modifier = Modifier,
                    scaffoldState = scaffoldState
                ) {
                }
                Column {
                    OutlinedTextField(value = strName, onValueChange = { strName = it })
                    Header(onClickRegister = {
                        isLogin = !isLogin
                        if (strName.isEmpty()) {
                            showSnackbar("please insert name", coroutineScope, scaffoldState)
                        } else {
                            addItem(strName)
                            strName = ""
                        }
                    })
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = if (isLogin) "You're Login as $strName" else "You're guest"
                    )
                    LazyColumn(

                        contentPadding = PaddingValues(vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(itemsModel.size) { num ->
                            itemLayout(number = itemsModel[num])
                        }
                    }
                }
            }
        }
    }


    fun showSnackbar(
        message: String,
        coroutineScope: CoroutineScope,
        scaffoldState: ScaffoldState
    ) {
        coroutineScope.launch { // using the `coroutineScope` to `launch` showing the snackbar
            // taking the `snackbarHostState` from the attached `scaffoldState`
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Dismiss"
            )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> {}
            }
        }
    }

    fun addItem(title: String) {
        itemsModel.add(title)
    }

    @Composable
    fun itemLayout(number: String) {
        AnimatedVisibility(
            visible = true,
            exit = fadeOut(
                animationSpec = TweenSpec(200, 200, FastOutLinearInEasing)
            )
        ) {
            Column(
                modifier = Modifier.clickable {
                    //show message
                }
            ) {
                Text(modifier = Modifier.padding(8.dp), text = number)
                Divider(color = Color.Black, thickness = 1.dp)
            }
        }
    }

    @Composable
    fun Header(onClickRegister: () -> Unit) {
        Surface {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GreetingText()
                OutlinedButton(onClick = { onClickRegister() }) {
                    Text("Expand")
                }
            }
        }
    }

    @Preview
    @Composable
    fun GreetingText() {
        Surface(color = Color.White) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(fontSize = 18.sp, text = "Hai User")
                Text(fontSize = 14.sp, text = "Welcome to Mobile Legend")
            }
        }
    }
}