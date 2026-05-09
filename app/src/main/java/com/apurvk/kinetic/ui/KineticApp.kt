package com.apurvk.kinetic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsBike
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apurvk.kinetic.data.sample.KineticColors
import com.apurvk.kinetic.data.sample.SampleKineticData
import com.apurvk.kinetic.domain.model.CookedBatch
import com.apurvk.kinetic.domain.model.DashboardStat
import com.apurvk.kinetic.domain.model.KineticTab
import com.apurvk.kinetic.domain.model.MealSection
import com.apurvk.kinetic.domain.model.QuickAction
import com.apurvk.kinetic.domain.model.TodayTask
import com.apurvk.kinetic.domain.model.WeeklySignal
import com.apurvk.kinetic.domain.model.WorkoutItem
import com.apurvk.kinetic.ui.theme.KineticTheme

@Composable
fun KineticApp() {
    KineticTheme {
        var selectedTab by rememberSaveable { mutableStateOf(KineticTab.Today) }

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
                    KineticTab.Today -> TodayScreen()
                    KineticTab.Fitness -> FitnessScreen()
                    KineticTab.Diet -> DietScreen()
                    KineticTab.Plan -> PlanScreen()
                    KineticTab.Stats -> StatsScreen()
                }
            }
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
                icon = {
                    Icon(
                        imageVector = tab.icon(),
                        contentDescription = tab.label
                    )
                },
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
private fun TodayScreen() {
    ScreenShell(
        title = "Today",
        subtitle = "Saturday plan, logs, and next action"
    ) {
        item {
            TodayHero()
        }
        item {
            MetricGrid(stats = SampleKineticData.stats)
        }
        item {
            QuickActions(actions = SampleKineticData.quickActions)
        }
        item {
            SectionHeader("Today's rhythm", "4 checks")
        }
        items(SampleKineticData.todayTasks.size) { index ->
            TaskRow(task = SampleKineticData.todayTasks[index])
        }
    }
}

@Composable
private fun FitnessScreen() {
    ScreenShell(
        title = "Fitness",
        subtitle = "Cardio imports later, gym templates now"
    ) {
        item {
            TwoActionRow(
                left = QuickAction(
                    title = "Cardio",
                    detail = "Cycling, running, walking",
                    icon = Icons.AutoMirrored.Outlined.DirectionsBike,
                    accent = KineticColors.Blue
                ),
                right = QuickAction(
                    title = "Gym",
                    detail = "Strength A/B checklist",
                    icon = Icons.Outlined.FitnessCenter,
                    accent = KineticColors.Copper
                )
            )
        }
        item {
            SectionHeader("Current workout", "Strength A")
        }
        items(SampleKineticData.strengthA.size) { index ->
            WorkoutRow(item = SampleKineticData.strengthA[index])
        }
        item {
            VoicePreviewCard(
                title = "Voice backup",
                body = "Say what changed: extra sets, skipped exercises, weight used, or soreness notes."
            )
        }
    }
}

@Composable
private fun DietScreen() {
    ScreenShell(
        title = "Diet",
        subtitle = "Eat sections for fast logs"
    ) {
        item {
            DietSummaryCard()
        }
        item {
            SectionHeader("Eat", "6 sections")
        }
        items(SampleKineticData.meals.size) { index ->
            MealRow(meal = SampleKineticData.meals[index])
        }
    }
}

@Composable
private fun PlanScreen() {
    ScreenShell(
        title = "Plan",
        subtitle = "This week from food already cooked"
    ) {
        item {
            PlanHeroCard()
        }
        item {
            SectionHeader("Cooked now", "2 to 3 day view")
        }
        items(SampleKineticData.cookedBatches.size) { index ->
            BatchRow(batch = SampleKineticData.cookedBatches[index])
        }
        item {
            VoicePreviewCard(
                title = "Planning note",
                body = "Plan around cooked batches first, then fill gaps with paneer, dahi, soy chunks, or sprouts."
            )
        }
    }
}

@Composable
private fun StatsScreen() {
    ScreenShell(
        title = "Stats",
        subtitle = "Approximate signals, not medical-grade math"
    ) {
        item {
            MetricGrid(stats = SampleKineticData.stats)
        }
        item {
            SectionHeader("Weekly consistency", "current week")
        }
        items(SampleKineticData.weeklySignals.size) { index ->
            WeeklySignalRow(signal = SampleKineticData.weeklySignals[index])
        }
        item {
            VoicePreviewCard(
                title = "Later with Gemma",
                body = "Voice logs become structured JSON, then app code calculates protein, calories, burn, and trends."
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
private fun TodayHero() {
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
                        text = "Keep it easy, keep it logged",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Lunch is the next useful check.",
                        color = Color.White.copy(alpha = 0.72f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = {}, shape = RoundedCornerShape(8.dp)) {
                    Icon(Icons.Outlined.Mic, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Voice log")
                }
                OutlinedButton(onClick = {}, shape = RoundedCornerShape(8.dp)) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Manual", color = Color.White)
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
                    MetricCard(
                        stat = stat,
                        modifier = Modifier.weight(1f)
                    )
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
private fun QuickActions(actions: List<QuickAction>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        actions.forEach { action ->
            ActionCard(action = action, modifier = Modifier.width(154.dp))
        }
    }
}

@Composable
private fun TwoActionRow(left: QuickAction, right: QuickAction) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        ActionCard(action = left, modifier = Modifier.weight(1f))
        ActionCard(action = right, modifier = Modifier.weight(1f))
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
            Surface(
                shape = CircleShape,
                color = action.accent,
                modifier = Modifier.size(36.dp)
            ) {
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
private fun WorkoutRow(item: WorkoutItem) {
    ListCard {
        StatusIcon(completed = item.completed, accent = KineticColors.Copper)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(text = item.target, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
        }
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = KineticColors.Copper.copy(alpha = 0.1f)
        ) {
            Text(
                text = if (item.completed) "Done" else "Log",
                color = KineticColors.Copper,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun MealRow(meal: MealSection) {
    ListCard {
        StatusIcon(completed = meal.logged, accent = KineticColors.Moss)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = meal.name, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(text = meal.plan, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
        }
        Text(text = meal.protein, color = KineticColors.Moss, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun BatchRow(batch: CookedBatch) {
    ListCard {
        Surface(shape = CircleShape, color = KineticColors.Gold.copy(alpha = 0.16f), modifier = Modifier.size(42.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.RestaurantMenu, contentDescription = null, tint = KineticColors.Gold)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = batch.dish, color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
            Text(text = batch.servingsLeft, color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
            Text(text = batch.plannedFor, color = KineticColors.Muted, style = MaterialTheme.typography.bodySmall)
        }
        Text(text = batch.protein, color = KineticColors.Gold, style = MaterialTheme.typography.labelLarge)
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
private fun DietSummaryCard() {
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
                Text(text = "82g protein logged", color = KineticColors.Ink, style = MaterialTheme.typography.titleMedium)
                Text(text = "Lunch and dinner can close the gap.", color = KineticColors.Muted, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
private fun PlanHeroCard() {
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
            Text(
                text = "Lunch: chhole, 2 rotis, dahi, salad. Dinner: rajma if hungry, paneer sandwich if late.",
                color = KineticColors.Muted,
                style = MaterialTheme.typography.bodyMedium
            )
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
