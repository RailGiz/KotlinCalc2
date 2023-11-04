import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.TileMode
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle
import androidx.compose.ui.graphics.graphicsLayer

fun main() = singleWindowApplication {
    var result by remember { mutableStateOf("") }


    Box(  //фон карточки
        modifier = Modifier
            .fillMaxSize()
            /*.background(Brush.linearGradient(
                colors = listOf(Color(0xFFEB92BE), Color(0xFFFFEF78), Color(0xFF63C9B4))
            ))*/
            .background(Color(0xFFf3767c)),
        contentAlignment = Alignment.Center, // Выравнивание содержимого по центру
    )
    {
        Card(
            modifier = Modifier
                .width(350.dp) // Задаем ширину
                .height(500.dp) // Задаем высоту
                .padding(10.dp), // Отступы
            shape = RoundedCornerShape(45.dp), // Закругление
            elevation = 27.dp, // Эффект подъема над экраном
        ){
            BoxWithConstraints {
                Column {//разделение карточки на 2 части
                    Box( //часть для текста
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f) // Занимает часть высоты
                            .background(Color(0xFF39383d))
                    ) {
                        Box( //пространство для поля в котором текст
                            modifier = Modifier.fillMaxSize()
                                .padding(top = 50.dp), // Отступ сверху
                            contentAlignment = Alignment.TopCenter // Выравнивание содержимого по центру
                        ){
                            Box( //пространство для текста
                                modifier = Modifier
                                    .size(300.dp, 50.dp) // Фиксированный размер
                                    .background(Color(0xFFa2af77), shape = RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center) // Выравнивание содержимого по центру

                            {
                                Text(
                                    text = if (result.isBlank()) " " else result,
                                    modifier = Modifier.padding(16.dp), //отступы вокруг текста
                                    fontSize = 20.sp, //шрифт
                                    color = Color.Black //цвет текста

                                )
                            }
                        }

                    }

                    Box( //часть для клавиш
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f) // Занимает часть высоты
                            .background(Color(0xFF363434))
                    ) {
                        Box( //контейнер для клавиатуры
                            modifier = Modifier.fillMaxSize(), // Заполнить всю доступную область
                            contentAlignment = Alignment.TopCenter // Выравнивание содержимого по центру
                        ) {
                            Column {
                                Spacer(modifier = Modifier.height(10.dp))

                                val buttons = listOf("C", "M+", "÷", "x", "7", "8", "9", "-", "4", "5", "6", "+", "1", "2", "3", "0", ".", "=")
                                val colors = listOf(Color.Gray, Color.Gray, Color.Gray, Color.Gray, Color.White, Color.White, Color.White, Color.Gray, Color.White, Color.White, Color.White, Color.Gray, Color.White, Color.White, Color.White, Color.Gray, Color.White, Color.Red)
                                val widthModifiers = listOf(Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier.fillMaxWidth(0.5f), Modifier.fillMaxWidth(0.5f))
                                val heightModifiers = listOf(Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier, Modifier)
                                buttons.chunked(4).forEachIndexed { rowIndex, row ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(1.dp),
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    )  {
                                        row.forEachIndexed { colIndex, button ->
                                            val color = colors[rowIndex * 4 + colIndex]
                                            val widthModifier = widthModifiers[rowIndex * 4 + colIndex]
                                            val heightModifier = heightModifiers[rowIndex * 4 + colIndex]
                                            Button(
                                                onClick = {
                                                    when (button) {
                                                        "=" -> result = try {
                                                            result.split(" ").let { if (it.size < 3) "" else it[0].toInt().let { first ->
                                                                it[2].toInt().let { second ->
                                                                    when (it[1]) {
                                                                        "+" -> (first + second).toString()
                                                                        "-" -> (first - second).toString()
                                                                        "x" -> (first * second).toString()
                                                                        "÷" -> if (second != 0) (first / second).toString() else "Error"
                                                                        else -> ""
                                                                    }
                                                                }
                                                            } }
                                                        } catch (e: Exception) {
                                                            "Error"
                                                        }

                                                        "C" -> result = ""
                                                        else -> result += if (button in listOf("+", "-", "x", "÷")) " $button " else button
                                                    }
                                                },
                                                modifier = widthModifier.then(heightModifier),
                                                colors = ButtonDefaults.buttonColors(backgroundColor = color)
                                            ) {
                                                Text(text = button)
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(1.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
