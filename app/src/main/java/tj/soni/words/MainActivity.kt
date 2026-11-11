package tj.soni.words

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Black = Color(0xFF090909)
private val Panel = Color(0xFF171717)
private val Gold = Color(0xFFD4AF37)
private val Muted = Color(0xFFAAAAAA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SoniWordsApp() }
    }
}

@Composable
fun SoniWordsApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Gold,
            background = Black,
            surface = Panel,
            onPrimary = Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Scaffold(
            containerColor = Black,
            bottomBar = {
                NavigationBar(containerColor = Panel, contentColor = Gold) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Асосӣ") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Default.Book, contentDescription = "Words") },
                        label = { Text("Луғат") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Icon(Icons.Default.EmojiEvents, contentDescription = "Progress") },
                        label = { Text("Натиҷа") }
                    )
                }
            }
        ) { padding ->
            when (selectedTab) {
                0 -> HomeScreen(Modifier.padding(padding))
                1 -> WordsScreen(Modifier.padding(padding))
                else -> ProgressScreen(Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text("SONI WORDS", color = Gold, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
            Text("Забон омӯз, ҷаҳонро фатҳ кун.", color = Muted, fontSize = 14.sp)
        }
        item {
            Surface(color = Panel, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("СЕ ЗАБОН. ЯК МАҚСАД.", color = Gold, fontWeight = FontWeight.Bold)
                    Text("Тоҷикӣ  •  Русӣ  •  English", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Омӯзишро аз имрӯз оғоз кун.", color = Muted)
                    Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Gold)) {
                        Text("Оғози омӯзиш", color = Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item { SectionTitle("Категорияҳои луғат") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CategoryCard("👋", "Салом", Modifier.weight(1f))
                CategoryCard("🏠", "Оила", Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CategoryCard("🍎", "Ғизо", Modifier.weight(1f))
                CategoryCard("✈️", "Сафар", Modifier.weight(1f))
            }
        }
        item { SectionTitle("Калимаи рӯз") }
        item { WordCard("Beautiful", "Зебо", "Красивый / Красивая") }
    }
}

@Composable
fun WordsScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Луғатҳо", color = Gold, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ҷустуҷӯи калима...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )
        Text("Дар версияи навбатӣ луғати пурраи се забон илова мешавад.", color = Muted)
        WordCard("Hello", "Салом", "Привет")
        WordCard("Book", "Китоб", "Книга")
        WordCard("Friend", "Дӯст", "Друг")
    }
}

@Composable
fun ProgressScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Натиҷаи омӯзиш", color = Gold, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        WordCard("0", "Калимаи азёдшуда", "Ҳадаф: ҳар рӯз 20 калима")
        WordCard("0", "Рӯзҳои пайдарпай", "Ҳар рӯз каме омӯз!")
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun CategoryCard(emoji: String, title: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = Panel, shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 27.sp)
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun WordCard(english: String, tajik: String, russian: String) {
    Surface(color = Panel, shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(english, color = Gold, fontSize = 21.sp, fontWeight = FontWeight.Bold)
            Text(tajik, color = Color.White, fontSize = 18.sp)
            Text(russian, color = Muted, fontSize = 14.sp)
        }
    }
}
