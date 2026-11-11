package tj.soni.words

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

data class Word(
    val id: Int,
    val english: String,
    val tajik: String,
    val russian: String,
    val category: String
)

private val wordList = listOf(
    Word(1, "Hello", "Салом", "Привет", "Салом"),
    Word(2, "Good morning", "Субҳ ба хайр", "Доброе утро", "Салом"),
    Word(3, "Goodbye", "Хайр", "До свидания", "Салом"),
    Word(4, "Thank you", "Ташаккур", "Спасибо", "Салом"),
    Word(5, "Family", "Оила", "Семья", "Оила"),
    Word(6, "Mother", "Модар", "Мать", "Оила"),
    Word(7, "Father", "Падар", "Отец", "Оила"),
    Word(8, "Brother", "Бародар", "Брат", "Оила"),
    Word(9, "Apple", "Себ", "Яблоко", "Ғизо"),
    Word(10, "Bread", "Нон", "Хлеб", "Ғизо"),
    Word(11, "Water", "Об", "Вода", "Ғизо"),
    Word(12, "Milk", "Шир", "Молоко", "Ғизо"),
    Word(13, "Travel", "Сафар", "Путешествие", "Сафар"),
    Word(14, "Airport", "Фурудгоҳ", "Аэропорт", "Сафар"),
    Word(15, "Ticket", "Чипта", "Билет", "Сафар"),
    Word(16, "Beautiful", "Зебо", "Красивый / Красивая", "Дигар"),
    Word(17, "Book", "Китоб", "Книга", "Дигар"),
    Word(18, "Friend", "Дӯст", "Друг", "Дигар"),
    Word(19, "School", "Мактаб", "Школа", "Дигар"),
    Word(20, "Love", "Муҳаббат", "Любовь", "Дигар")
)

private val categories = listOf(
    "Ҳама", "Салом", "Оила", "Ғизо", "Сафар", "Дигар"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SoniWordsApp() }
    }
}

@Composable
fun SoniWordsApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf("Ҳама") }
    val favorites = remember { mutableStateListOf<Int>() }
    val learned = remember { mutableStateListOf<Int>() }

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
                NavigationBar(containerColor = Panel) {
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
                0 -> HomeScreen(
                    modifier = Modifier.padding(padding),
                    onStart = { selectedTab = 1 },
                    onCategory = {
                        selectedCategory = it
                        selectedTab = 1
                    }
                )
                1 -> WordsScreen(
                    modifier = Modifier.padding(padding),
                    selectedCategory = selectedCategory,
                    onCategoryChange = { selectedCategory = it },
                    favorites = favorites,
                    learned = learned
                )
                else -> ProgressScreen(
                    modifier = Modifier.padding(padding),
                    learnedCount = learned.size,
                    favoriteCount = favorites.size,
                    learned = learned
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    onCategory: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text("SONI WORDS", color = Gold, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
            Text("Забон омӯз, ҷаҳонро фатҳ кун.", color = Muted)
        }
        item {
            Surface(color = Panel, shape = RoundedCornerShape(22.dp)) {
                Column(
                    Modifier.fillMaxWidth().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("СЕ ЗАБОН. ЯК МАҚСАД.", color = Gold, fontWeight = FontWeight.Bold)
                    Text(
                        "Тоҷикӣ  •  Русӣ  •  English",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Омӯзишро аз имрӯз оғоз кун.", color = Muted)
                    Button(
                        onClick = onStart,
                        colors = ButtonDefaults.buttonColors(containerColor = Gold)
                    ) {
                        Text("Оғози омӯзиш", color = Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item { SectionTitle("Категорияҳои луғат") }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CategoryCard("👋", "Салом", Modifier.weight(1f)) { onCategory("Салом") }
                CategoryCard("🏠", "Оила", Modifier.weight(1f)) { onCategory("Оила") }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CategoryCard("🍎", "Ғизо", Modifier.weight(1f)) { onCategory("Ғизо") }
                CategoryCard("✈️", "Сафар", Modifier.weight(1f)) { onCategory("Сафар") }
            }
        }
        item { SectionTitle("Калимаи рӯз") }
        item { WordCard(wordList[15]) }
    }
}

@Composable
fun WordsScreen(
    modifier: Modifier = Modifier,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    favorites: MutableList<Int>,
    learned: MutableList<Int>
) {
    var query by remember { mutableStateOf("") }
    var showFavorites by remember { mutableStateOf(false) }

    val filteredWords = wordList.filter { word ->
        val matchesCategory =
            selectedCategory == "Ҳама" || word.category == selectedCategory
        val matchesQuery =
            word.english.contains(query, ignoreCase = true) ||
            word.tajik.contains(query, ignoreCase = true) ||
            word.russian.contains(query, ignoreCase = true)
        val matchesFavorite = !showFavorites || favorites.contains(word.id)

        matchesCategory && matchesQuery && matchesFavorite
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Луғатҳо", color = Gold, fontSize = 26.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ҷустуҷӯи калима...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = !showFavorites,
                onClick = { showFavorites = false },
                label = { Text("Ҳама") }
            )
            FilterChip(
                selected = showFavorites,
                onClick = { showFavorites = true },
                label = { Text("★ Дӯстдошта") }
            )
        }

        Text("Категория:", color = Muted, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.take(3).forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { onCategoryChange(category) },
                    label = { Text(category) }
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.drop(3).forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { onCategoryChange(category) },
                    label = { Text(category) }
                )
            }
        }

        Text("${filteredWords.size} калима ёфт шуд", color = Muted)

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredWords, key = { it.id }) { word ->
                WordCard(
                    word = word,
                    isFavorite = favorites.contains(word.id),
                    isLearned = learned.contains(word.id),
                    onFavorite = {
                        if (favorites.contains(word.id)) favorites.remove(word.id)
                        else favorites.add(word.id)
                    },
                    onLearned = {
                        if (!learned.contains(word.id)) learned.add(word.id)
                        else learned.remove(word.id)
                    }
                )
            }
        }
    }
}

@Composable
fun ProgressScreen(
    modifier: Modifier = Modifier,
    learnedCount: Int,
    favoriteCount: Int,
    learned: List<Int>
) {
    Column(
        modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Натиҷаи омӯзиш", color = Gold, fontSize = 26.sp, fontWeight = FontWeight.Bold)

        WordCard(
            Word(100, learnedCount.toString(), "Калимаи азёдшуда", "Выучено слов", "Натиҷа")
        )
        WordCard(
            Word(101, favoriteCount.toString(), "Калимаи дӯстдошта", "Избранное", "Натиҷа")
        )

        Text("Пешрафти омӯзиш", color = Color.White, fontWeight = FontWeight.Bold)
        LinearProgressIndicator(
            progress = { learnedCount / 20f },
            modifier = Modifier.fillMaxWidth(),
            color = Gold,
            trackColor = Panel
        )
        Text("$learnedCount аз 20 калима", color = Muted)
        Text("Ҳар рӯз 20 калима омӯз!", color = Gold)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun CategoryCard(
    emoji: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        color = Panel,
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 27.sp)
            Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun WordCard(
    word: Word,
    isFavorite: Boolean = false,
    isLearned: Boolean = false,
    onFavorite: (() -> Unit)? = null,
    onLearned: (() -> Unit)? = null
) {
    Surface(
        color = Panel,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    word.english,
                    color = Gold,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (onFavorite != null) {
                    Text(
                        if (isFavorite) "★" else "☆",
                        color = Gold,
                        fontSize = 26.sp,
                        modifier = Modifier.clickable { onFavorite() }
                    )
                }
            }
            Text(word.tajik, color = Color.White, fontSize = 18.sp)
            Text(word.russian, color = Muted, fontSize = 14.sp)

            if (onLearned != null) {
                TextButton(onClick = onLearned) {
                    Text(
                        if (isLearned) "✓ Азёд шуд" else "+ Азёд кардам",
                        color = Gold
                    )
                }
            }
        }
    }
}
