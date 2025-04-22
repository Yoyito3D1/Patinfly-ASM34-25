package cat.deim.asm34.patinfly.presentation.pricing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import cat.deim.asm34.patinfly.domain.models.SystemPricingPlan
import androidx.compose.ui.res.stringResource
import cat.deim.asm34.patinfly.R

@Composable
fun PricingScreen(
    viewModel: PricingViewModel,
    onBackToMain: () -> Unit
) {
    val plans by viewModel.plans.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPlans()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.pricing_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackToMain, modifier = Modifier.align(Alignment.End)) {
            Text(stringResource(R.string.back_to_main))
        }

        Spacer(modifier = Modifier.height(16.dp))

        val bestPlan = plans.maxByOrNull { it.price } // o it.pricePerMinute, si prefieres

        LazyColumn {
            items(plans) { plan ->
                val isBest = plan == bestPlan
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = if (isBest) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    else CardDefaults.cardColors()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(R.string.plan_name, plan.name),
                            style = if (isBest) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge
                        )
                        Text(stringResource(R.string.plan_description, plan.description))
                        Text(stringResource(R.string.plan_price, plan.price, plan.currency))
                        Text(stringResource(R.string.plan_per_minute, plan.pricePerMinute))
                        Text(stringResource(R.string.plan_per_km, plan.pricePerKm))
                        Text(stringResource(R.string.plan_taxable, if (plan.isTaxable) "Yes" else "No"))
                    }
                }
            }
        }
    }
}
