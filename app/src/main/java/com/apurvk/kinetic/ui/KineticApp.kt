package com.apurvk.kinetic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsBike
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apurvk.kinetic.data.local.CardioSessionEntity
import com.apurvk.kinetic.data.local.CookedBatchEntity
import com.apurvk.kinetic.data.local.GymExerciseLogEntity
import com.apurvk.kinetic.data.local.MealLogEntity
import com.apurvk.kinetic.data.sample.KineticColors
import com.apurvk.kinetic.domain.model.DashboardStat
import com.apurvk.kinetic.domain.model.KineticTab
import com.apurvk.kinetic.domain.model.QuickAction
import com.apurvk.kinetic.domain.model.TodayTask
import com.apurvk.kinetic.domain.model.WeeklySignal
import com.apurvk.kinetic.ui.theme.KineticTheme
import java.util.Locale

private val mealSections = listOf(
    "Pre-workout fruit",
    "Tea / drinks",
    "Breakfast",
    "Lunch",
    "Dinner",
    "Extras"
)

@Composable
fun KineticApp() {
    val context = LocalContext.current.applicationContext
    val viewModel: KineticViewModel = viewModel(factory = KineticViewModelFactory(context))
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    KineticTheme {
        var selectedTab by rememberSaveable { mutableStateOf(KineticTab.Today) }
        var mealDialogSection by rememberSaveable { mutableStateOf<String?>(null) }
        var showCardioDialog by rememberSaveable { mutableStateOf(false) }
        var showBatchDialog by rememberSaveable { mutableStateOf(false) }

        Scaffold(
            containerColor = KineticColors.Paper,
            bottomBar = {
                KineticBottomBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(KineticColors.Paper)
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    KineticTab.Today -> TodayScreen(
                        uiState = uiState,
                        onQuickAction = { selectedTab = it },
                        onMealLog = { mealDialogSection = it },
                        onCardioLog = { showCardioDialog = true },
                        onBatchAdd = { showBatchDialog = true },
                        onDeleteMeal = viewModel::deleteMeal,
                        onDeleteCardio = viewModel::deleteCardio
                    )
                    KineticTab.Fitness -> FitnessScreen(
                        uiState = uiState,
                        onTemplateSelected = viewModel::selectTemplate,
                        onDraftChanged = viewModel::updateGymDraft,
                        onSaveWorkout = viewModel::saveGymDraft,
                        onDeleteGymLog = viewModel::deleteGymLog,
                        onCardioLog = { showCardioDialog = true },
                        onDeleteCardio = viewModel::deleteCardio
                    )
                    KineticTab.Diet -> DietScreen(
                        uiState = uiState,
                        onMealLog = { mealDialogSection = it },
                        onDeleteMeal = viewModel::deleteMeal
                    )
                    KineticTab.Plan -> PlanScreen(
                        uiState = uiState,
                        onBatchAdd = { showBatchDialog = true },
                        onDeleteBatch = viewModel::deleteBatch
                    )
                    KineticTab.Stats -> StatsScreen(uiState = uiState)
                }
            }
        }

        mealDialogSection?.let { section ->
            MealLogDialog(
                section = section,
                onDismiss = { mealDialogSection = null },
                onSave = { description ->
                    viewModel.addMeal(section, description)
                    mealDialogSection = null
                }
            )
        }

        if (showCardioDialog) {
            CardioDialog(
                onDismiss = { showCardioDialog = false },
                onSave = { type, duration, distance, intensity, notes ->
                    viewModel.addCardio(type, duration, distance, intensity, notes)
                    showCardioDialog = false
                }
            )
        }

        if (showBatchDialog) {
            BatchDialog(
                onDismiss = { showBatchDialog = false },
                onSave = { dish, servings, plan ->
                    viewModel.addBatch(dish, servings, plan)
                    showBatchDialog = false
                }
            )
        }
    }
}

@Composable
private fun KineticBottomBar(
    selectedTab: KineticTab,
    onTabSelected: (KineticTab) -> Unit
) {
    NavigationBar(
        containerColor = KineticColors.Card,
        tonalElevation = 0.dp
    ) {
        KineticTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = { Icon(imageVector = tab.icon(), contentDescription = tab.label) },
                label = {
                    Text(
                        text = tab.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

private fun KineticTab.icon(): ImageVector = when (this) {
    KineticTab.Today -> Icons.Outlined.Home
    KineticTab.Fitness -> Icons.Outlined.FitnessCenter
    KineticTab.Diet -> Icons.Outlined.Restaurant
    KineticTab.Plan -> Icons.Outlined.CalendarMonth
    KineticTab.Stats -> Icons.Outlined.QueryStats
}

@Composable
private fun TodayScreen(
    uiState: KineticUiState,
    onQuickAction: (KineticTab) -> Unit,
    onMealLog: (String) -> Unit,
    onCardioLog: () -> Unit,
    onBatchAdd: () -> Unit,
    onDeleteMeal: (MealLogEntity) -> Unit,
    onDeleteCardio: (CardioSessionEntity) -> Unit
) {
    ScreenShell(
        title = "Today",
        subtitle = "${uiState.dateKey} plan, logs, and next action"
    ) {
        item {
            TodayHero(
                protein = uiState.proteinGrams,
                onManualMeal = { onMealLog("Lunch") }
            )
        }
        item {
            MetricGrid(stats = dashboardStats(uiState))
        }
        item {
            QuickActions(
                actions = quickActions(),
                onAction = { action ->
                    when (action.title) {
                        "Cardio" -> onCardioLog()
                        "Meal" -> onQuickAction(KineticTab.Diet)
                        "Plan" -> onBatchAdd()
                        "Gym" -> onQuickAction(KineticTab.Fitness)
                        else -> onQuickAction(KineticTab.Diet)
                    }
                }
            )
        }
        item {
            SectionHeader("Today's rhythm", "${uiState.loggedMealSections}/6 meals")
        }
        items(todayTasks(uiState).size) { index ->
            TaskRow(task = todayTasks(uiState)[index])
        }
        if (uiState.meals.isNotEmpty()) {
            item { SectionHeader("Meals logged", "${uiState.meals.size}") }
            items(uiState.meals.size) { index ->
                MealLogRow(meal = uiState.meals[index], onDelete = onDeleteMeal)
            }
        }
        if (uiState.cardio.isNotEmpty()) {
            item { SectionHeader("Cardio logged", "${uiState.cardio.size}") }
            items(uiState.cardio.size) { index ->
                CardioRow(cardio = uiState.cardio[index], onDelete = onDeleteCardio)
            }
        }
    }
}

@Composable
private fun FitnessScreen(
    uiState: KineticUiState,
    onTemplateSelected: (String) -> Unit,
    onDraftChanged: (String, Boolean?, String?, String?, String?) -> Unit,
    onSaveWorkout: () -> Unit,
    onDeleteGymLog: (GymExerciseLogEntity) -> Unit,
    onCardioLog: () -> Unit,
    onDeleteCardio: (CardioSessionEntity) -> Unit
) {
    ScreenShell(
        title = "Fitness",
        subtitle = "Manual cardio and fixed gym templates"
    ) {
        item {
            TwoActionRow(
                left = QuickAction(
                    title = "Cardio",
                    detail = "Log ride, run, or walk",
                    icon = Icons.AutoMirrored.Outlined.DirectionsBike,
                    accent = KineticColors.Blue
                ),
                right = QuickAction(
                    title = "Gym",
                    detail = "Save Strength A/B work",
                    icon = Icons.Outlined.FitnessCenter,
                    accent = KineticColors.Copper
                ),
                onLeftClick = onCardioLog,
                onRightClick = {}
            )
        }
        item {
            TemplateSelector(
                selectedTemplate = uiState.selectedTemplate,
                onTemplateSelected = onTemplateSelected
            )
        }
        item {
            SectionHeader("Workout draft", uiState.selectedTemplate)
        }
        items(uiState.gymDrafts.size) { index ->
            GymDraftRow(
                draft = uiState.gymDrafts[index],
                onDraftChanged = onDraftChanged
            )
        }
        item {
            Button(
                onClick = onSaveWorkout,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Outlined.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save completed exercises")
            }
        }
        if (uiState.gymLogs.isNotEmpty()) {
            item { SectionHeader("Saved gym work", "${uiState.gymLogs.size}") }
            items(uiState.gymLogs.size) { index ->
                GymLogRow(log = uiState.gymLogs[index], onDelete = onDeleteGymLog)
            }
        }
        if (uiState.cardio.isNotEmpty()) {
            item { SectionHeader("Cardio today", "${uiState.exerciseCalories} kcal") }
            items(uiState.cardio.size) { index ->
                CardioRow(cardio = uiState.cardio[index], onDelete = onDeleteCardio)
            }
        }
    }
}

@Composable
private fun DietScreen(
    uiState: KineticUiState,
    onMealLog: (String) -> Unit,
    onDeleteMeal: (MealLogEntity) -> Unit
) {
    ScreenShell(
        title = "Diet",
        subtitle = "Log meals with rough protein and calorie estimates"
    ) {
        item {
            DietSummaryCard(uiState)
        }
        item {
            SectionHeader("Eat", "tap a section")
        }
        items(mealSections.size) { index ->
            val section = mealSections[index]
            val logs = uiState.meals.filter { it.section == section }
            MealSectionRow(
                section = section,
                logs = logs,
                onMealLog = onMealLog
            )
        }
        if (uiState.meals.isNotEmpty()) {
            item { SectionHeader("Today's meal logs", "${uiState.meals.size}") }
            items(uiState.meals.size) { index ->
                MealLogRow(meal = uiState.meals[index], onDelete = onDeleteMeal)
            }
        }
    }
}

@Composable
private fun PlanScreen(
    uiState: KineticUiState,
    onBatchAdd: () -> Unit,
    onDeleteBatch: (CookedBatchEntity) -> Unit
) {
    ScreenShell(
        title = "Plan",
        subtitle = "Cooked food and next meals"
    ) {
        item {
            PlanHeroCard(uiState)
        }
        item {
            Button(onClick = onBatchAdd, shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add cooked batch")
            }
        }
        item {
            SectionHeader("Cooked now", "${uiState.batches.size} batches")
        }
        if (uiState.batches.isEmpty()) {
            item {
                EmptyCard("No cooked batches yet. Add chhole, rajma, dal, paneer filling, or whatever is ready.")
            }
        }
        items(uiState.batches.size) { index ->
            BatchRow(batch = uiState.batches[index], onDelete = onDeleteBatch)
        }
    }
}

@Composable
private fun StatsScreen(uiState: KineticUiState) {
    ScreenShell(
        title = "Stats",
        subtitle = "Local estimates from today's saved logs"
    ) {
        item {
            MetricGrid(stats = dashboardStats(uiState))
        }
        item {
            SectionHeader("Weekly consistency preview", "today only")
        }
        items(weeklySignals(uiState).size) { index ->
            WeeklySignalRow(signal = weeklySignals(uiState)[index])
        }
        item {
            VoicePreviewCard(
                title = "Calculation rule",
                body = "Known foods map to rough defaults. Unknown foods save with 0 estimates for now and can be improved later."
            )
        }
    }
}

@Composable
private fun ScreenShell(
    title: String,
    subtitle: String,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = KineticColors.Ink
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = KineticColors.Muted
            )
        }
        content()
    }
}

@Composable
private fun TodayHero(protein: Int, onManualMeal: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Ink),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (protein >= 105) "Protein target is in range" else "Next best log: lunch or dinner",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "$protein g logged toward a 105-140 g target.",
                        color = Color.White.copy(alpha = 0.72f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onManualMeal, shape = RoundedCornerShape(8.dp)) {
                    Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Log lunch")
                }
                OutlinedButton(onClick = {}, shape = RoundedCornerShape(8.dp)) {
                    Icon(
                        Icons.Outlined.Mic,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Voice later", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun MetricGrid(stats: List<DashboardStat>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        stats.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { stat ->
                    MetricCard(stat = stat, modifier = Modifier.weight(1f))
                }
                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun MetricCard(stat: DashboardStat, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.border(1.dp, KineticColors.Line, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = stat.label, color = KineticColors.Muted, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stat.value, color = stat.accent, style = MaterialTheme.typography.headlineSmall)
            Text(text = stat.helper, color = KineticColors.Muted, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun QuickActions(actions: List<QuickAction>, onAction: (QuickAction) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        actions.forEach { action ->
            ActionCard(
                action = action,
                modifier = Modifier
                    .width(154.dp)
                    .clickable { onAction(action) }
            )
        }
    }
}

@Composable
private fun TwoActionRow(
    left: QuickAction,
    right: QuickAction,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        ActionCard(
            action = left,
            modifier = Modifier
                .weight(1f)
                .clickable { onLeftClick() }
        )
        ActionCard(
            action = right,
            modifier = Modifier
                .weight(1f)
                .clickable { onRightClick() }
        )
    }
}

@Composable
private fun ActionCard(action: QuickAction, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = action.accent.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Surface(shape = CircleShape, color = action.accent, modifier = Modifier.size(36.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(action.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = action.title, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(
                text = action.detail,
                color = KineticColors.Muted,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String, trailing: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = KineticColors.Ink,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        AssistChip(onClick = {}, label = { Text(trailing) })
    }
}

@Composable
private fun TaskRow(task: TodayTask) {
    ListCard {
        StatusIcon(completed = task.completed, accent = task.accent)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = task.title, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(text = task.detail, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
        }
        Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = KineticColors.Muted)
    }
}

@Composable
private fun MealSectionRow(
    section: String,
    logs: List<MealLogEntity>,
    onMealLog: (String) -> Unit
) {
    ListCard {
        StatusIcon(completed = logs.isNotEmpty(), accent = KineticColors.Moss)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = section, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(
                text = if (logs.isEmpty()) "No log yet" else "${logs.sumOf { it.proteinGrams }}g protein, ${logs.sumOf { it.calories }} kcal",
                color = KineticColors.Muted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Button(onClick = { onMealLog(section) }, shape = RoundedCornerShape(8.dp)) {
            Text(if (logs.isEmpty()) "Log" else "Add")
        }
    }
}

@Composable
private fun MealLogRow(meal: MealLogEntity, onDelete: (MealLogEntity) -> Unit) {
    ListCard {
        Surface(shape = CircleShape, color = KineticColors.Moss.copy(alpha = 0.14f), modifier = Modifier.size(40.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Restaurant, contentDescription = null, tint = KineticColors.Moss)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = meal.section, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(text = meal.description, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "${meal.proteinGrams}g protein - ${meal.calories} kcal",
                color = KineticColors.Moss,
                style = MaterialTheme.typography.bodySmall
            )
        }
        DeleteButton { onDelete(meal) }
    }
}

@Composable
private fun TemplateSelector(selectedTemplate: String, onTemplateSelected: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        listOf("Strength A", "Strength B").forEach { template ->
            val selected = selectedTemplate == template
            Button(
                onClick = { onTemplateSelected(template) },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f),
                enabled = !selected
            ) {
                Text(template)
            }
        }
    }
}

@Composable
private fun GymDraftRow(
    draft: GymExerciseDraft,
    onDraftChanged: (String, Boolean?, String?, String?, String?) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, KineticColors.Line, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = draft.completed,
                    onCheckedChange = { onDraftChanged(draft.name, it, null, null, null) }
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = draft.name, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
                    Text(text = draft.target, color = KineticColors.Muted, style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CompactField(
                    value = draft.sets,
                    label = "Sets",
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                    onValueChange = { onDraftChanged(draft.name, null, it.onlyCompactInput(), null, null) }
                )
                CompactField(
                    value = draft.reps,
                    label = "Reps",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.weight(1f),
                    onValueChange = { onDraftChanged(draft.name, null, null, it.onlyCompactInput(), null) }
                )
                CompactField(
                    value = draft.weight,
                    label = "Weight",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.weight(1f),
                    onValueChange = { onDraftChanged(draft.name, null, null, null, it.onlyCompactInput()) }
                )
            }
        }
    }
}

@Composable
private fun GymLogRow(log: GymExerciseLogEntity, onDelete: (GymExerciseLogEntity) -> Unit) {
    ListCard {
        StatusIcon(completed = true, accent = KineticColors.Copper)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = log.exerciseName, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${log.templateName}: ${log.sets} sets, ${log.reps} reps, ${log.weight} weight",
                color = KineticColors.Muted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        DeleteButton { onDelete(log) }
    }
}

@Composable
private fun CardioRow(cardio: CardioSessionEntity, onDelete: (CardioSessionEntity) -> Unit) {
    ListCard {
        Surface(shape = CircleShape, color = KineticColors.Blue.copy(alpha = 0.14f), modifier = Modifier.size(40.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Timer, contentDescription = null, tint = KineticColors.Blue)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = cardio.type, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${cardio.durationMinutes} min ${cardio.intensity.lowercase()} - ${cardio.caloriesBurned} kcal",
                color = KineticColors.Muted,
                style = MaterialTheme.typography.bodyMedium
            )
            if (cardio.distance.isNotBlank() || cardio.notes.isNotBlank()) {
                Text(
                    text = listOf(cardio.distance, cardio.notes).filter { it.isNotBlank() }.joinToString(" - "),
                    color = KineticColors.Muted,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        DeleteButton { onDelete(cardio) }
    }
}

@Composable
private fun BatchRow(batch: CookedBatchEntity, onDelete: (CookedBatchEntity) -> Unit) {
    ListCard {
        Surface(shape = CircleShape, color = KineticColors.Gold.copy(alpha = 0.16f), modifier = Modifier.size(42.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.RestaurantMenu, contentDescription = null, tint = KineticColors.Gold)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = batch.dishName, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(text = "${batch.servingsLeft} servings left", color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
            Text(text = batch.plannedFor.ifBlank { "No plan yet" }, color = KineticColors.Muted, style = MaterialTheme.typography.bodySmall)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${batch.proteinPerServing}g",
                color = KineticColors.Gold,
                style = MaterialTheme.typography.labelLarge
            )
            DeleteButton { onDelete(batch) }
        }
    }
}

@Composable
private fun WeeklySignalRow(signal: WeeklySignal) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, KineticColors.Line, RoundedCornerShape(8.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = signal.label,
                    color = KineticColors.Ink,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Text(text = signal.value, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { signal.progress },
                color = signal.accent,
                trackColor = signal.accent.copy(alpha = 0.12f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
    }
}

@Composable
private fun DietSummaryCard(uiState: KineticUiState) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Moss.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = CircleShape, color = KineticColors.Moss, modifier = Modifier.size(42.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.Restaurant, contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${uiState.proteinGrams}g protein logged",
                    color = KineticColors.Ink,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${uiState.caloriesIn} kcal across ${uiState.loggedMealSections} meal sections.",
                    color = KineticColors.Muted,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun PlanHeroCard(uiState: KineticUiState) {
    val nextPlan = uiState.batches.firstOrNull()?.let {
        "${it.dishName}: ${it.servingsLeft} servings left, planned for ${it.plannedFor.ifBlank { "later" }}."
    } ?: "Add cooked food to build the next 2-3 days."

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Blue.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Schedule, contentDescription = null, tint = KineticColors.Blue)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Next meal plan", color = KineticColors.Ink, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = nextPlan, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun VoicePreviewCard(title: String, body: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, KineticColors.Line, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = CircleShape, color = KineticColors.Ink, modifier = Modifier.size(42.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.Mic, contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
                Text(text = body, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun EmptyCard(text: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, KineticColors.Line, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text,
            color = KineticColors.Muted,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun ListCard(content: @Composable RowScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = KineticColors.Card),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, KineticColors.Line, RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
private fun StatusIcon(completed: Boolean, accent: Color) {
    Surface(
        shape = CircleShape,
        color = if (completed) accent.copy(alpha = 0.14f) else Color.Transparent,
        modifier = Modifier
            .size(32.dp)
            .border(1.dp, if (completed) accent else KineticColors.Line, CircleShape)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = if (completed) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = if (completed) "Completed" else "Pending",
                tint = if (completed) accent else KineticColors.Muted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun DeleteButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete",
            tint = KineticColors.Muted
        )
    }
}

@Composable
private fun CompactField(
    value: String,
    label: String,
    keyboardType: KeyboardType,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier
    )
}

@Composable
private fun MealLogDialog(
    section: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var description by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log $section") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Example: 2 rotis, chhole, dahi, salad",
                    color = KineticColors.Muted,
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("What did you eat?") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(description) }, shape = RoundedCornerShape(8.dp)) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun CardioDialog(
    onDismiss: () -> Unit,
    onSave: (String, Int, String, String, String) -> Unit
) {
    var type by rememberSaveable { mutableStateOf("Cycling") }
    var duration by rememberSaveable { mutableStateOf("") }
    var distance by rememberSaveable { mutableStateOf("") }
    var intensity by rememberSaveable { mutableStateOf("Easy") }
    var notes by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log cardio") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SegmentedChoices(
                    values = listOf("Cycling", "Running", "Walking"),
                    selected = type,
                    onSelected = { type = it }
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it.onlyDigits() },
                    label = { Text("Minutes") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = distance,
                    onValueChange = { distance = it.onlyCompactInput() },
                    label = { Text("Distance optional") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                SegmentedChoices(
                    values = listOf("Recovery", "Easy", "Moderate", "Hard"),
                    selected = intensity,
                    onSelected = { intensity = it }
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes optional") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(type, duration.toIntOrNull() ?: 0, distance, intensity, notes) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun BatchDialog(
    onDismiss: () -> Unit,
    onSave: (String, Int, String) -> Unit
) {
    var dish by rememberSaveable { mutableStateOf("") }
    var servings by rememberSaveable { mutableStateOf("") }
    var plannedFor by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add cooked batch") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = dish,
                    onValueChange = { dish = it },
                    label = { Text("Dish") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = servings,
                    onValueChange = { servings = it.onlyDigits() },
                    label = { Text("Servings left") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = plannedFor,
                    onValueChange = { plannedFor = it },
                    label = { Text("Planned for") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(dish, servings.toIntOrNull() ?: 0, plannedFor) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun SegmentedChoices(
    values: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        values.forEach { value ->
            if (selected == value) {
                Button(onClick = { onSelected(value) }, shape = RoundedCornerShape(8.dp)) {
                    Text(value)
                }
            } else {
                OutlinedButton(onClick = { onSelected(value) }, shape = RoundedCornerShape(8.dp)) {
                    Text(value)
                }
            }
        }
    }
}

private fun dashboardStats(uiState: KineticUiState): List<DashboardStat> = listOf(
    DashboardStat("Protein", "${uiState.proteinGrams}g", "${(105 - uiState.proteinGrams).coerceAtLeast(0)}g left", KineticColors.Moss),
    DashboardStat("Intake", "${uiState.caloriesIn}", "kcal logged", KineticColors.Copper),
    DashboardStat("Burn", "${uiState.exerciseCalories}", "kcal cardio est.", KineticColors.Blue),
    DashboardStat("Meals", "${uiState.loggedMealSections}/6", "sections logged", KineticColors.Gold)
)

private fun quickActions(): List<QuickAction> = listOf(
    QuickAction("Meal", "Add food to today", Icons.Outlined.Restaurant, KineticColors.Moss),
    QuickAction("Cardio", "Ride, run, or walk", Icons.AutoMirrored.Outlined.DirectionsBike, KineticColors.Blue),
    QuickAction("Gym", "Strength A/B checklist", Icons.Outlined.FitnessCenter, KineticColors.Copper),
    QuickAction("Plan", "Cooked batches", Icons.Outlined.CalendarMonth, KineticColors.Gold)
)

private fun todayTasks(uiState: KineticUiState): List<TodayTask> = listOf(
    TodayTask("Cardio", "${uiState.cardio.size} session(s), ${uiState.exerciseCalories} kcal", uiState.cardio.isNotEmpty(), KineticColors.Blue),
    TodayTask("Gym", "${uiState.gymExercisesCompleted} exercises saved", uiState.gymCompleted, KineticColors.Copper),
    TodayTask("Meals", "${uiState.loggedMealSections} of 6 sections logged", uiState.loggedMealSections >= 3, KineticColors.Moss),
    TodayTask("Plan", "${uiState.batches.size} cooked batches available", uiState.batches.isNotEmpty(), KineticColors.Gold)
)

private fun weeklySignals(uiState: KineticUiState): List<WeeklySignal> = listOf(
    WeeklySignal("Meal sections", "${uiState.loggedMealSections} of 6", uiState.loggedMealSections / 6f, KineticColors.Moss),
    WeeklySignal("Gym", "${uiState.gymExercisesCompleted} exercises", (uiState.gymExercisesCompleted / 6f).coerceAtMost(1f), KineticColors.Copper),
    WeeklySignal("Cardio", "${uiState.cardio.size} session(s)", (uiState.cardio.size / 1f).coerceAtMost(1f), KineticColors.Blue),
    WeeklySignal("Protein", "${uiState.proteinGrams}g", (uiState.proteinGrams / 105f).coerceAtMost(1f), KineticColors.Gold)
)

private fun String.onlyDigits(): String = filter { it.isDigit() }.take(4)

private fun String.onlyCompactInput(): String = filter { it.isLetterOrDigit() || it == '.' || it == '-' || it == ' ' }.take(18)

private fun String.titleCase(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}
