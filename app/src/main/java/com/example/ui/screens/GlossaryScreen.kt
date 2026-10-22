package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Gold600

data class GlossaryEntry(
    val englishTerm: String,
    val moduleCode: String, // "BLaw", "ECON", "CS", "PUB", "PED"
    val moduleName: String,
    val spokenChichewaMetaphor: String,
    val academicTranslation: String
)

val academicGlossaryEntries = listOf(
    // === BUSINESS LAW (BLaw) ===
    GlossaryEntry(
        englishTerm = "Doctrine of Consideration",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Mtengo wovomerezeka m'malamulo umene mbali iliyonse imalipira kuti lonjezo la mnzake likhale lamphamvu ku khoti",
        academicTranslation = "The bedrock common law requirement that an enforceable covenant requires reciprocal economic value, forbearance, or benefit moving from the promisee (Currie v Misa)."
    ),
    GlossaryEntry(
        englishTerm = "Past Consideration",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Lonjezo logona pa chisamaliro kapena ntchito yomwe inachitika kale m'mbuyo popanda mgwirizano",
        academicTranslation = "The doctrine providing that an act executed prior to giving a promise cannot serve as consideration to support that promise (Roscorla v Thomas; Pao On exception)."
    ),
    GlossaryEntry(
        englishTerm = "Sufficiency vs. Adequacy of Consideration",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Khoti sililowelera ngati mtengo unali wotsika ngati masenti ochepa, bola ngati chinthucho chili ndi mtengo m'maso mwa lamulo",
        academicTranslation = "Courts necessitate that consideration possess recognized legal value ('sufficiency'), but steadfastly refuse to evaluate subjective economic parity or commercial equality ('adequacy')."
    ),
    GlossaryEntry(
        englishTerm = "Privity of Contract",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Anthu awiri akapangana mgwirizano, munthu wachitatu sangathe kusuma kapena kusumidwa pa mgwirizanowo",
        academicTranslation = "The principle stipulating that only contracting parties can acquire enforceable obligations or rights under an agreement (Tweddle v Atkinson; Dunlop v Selfridge; Kamwendo v RBM)."
    ),
    GlossaryEntry(
        englishTerm = "Breach of Condition vs. Warranty",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Kuswa mfundo yaikulu ya mgwirizano kumathetsa ntchito yonse; kuswa mfundo yaing'ono kumangopatsa ufulu wolipidwa chindapusa",
        academicTranslation = "Breach of a condition repudiates the foundational contract enabling termination and damages; breach of a subsidiary warranty merely justifies damages without discharge."
    ),
    GlossaryEntry(
        englishTerm = "Doctrine of Ultra Vires",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Kuchita zinthu zopitirira mphamvu zomwe kampani yapatsidwa m'malamulo ake a Memorandum of Association",
        academicTranslation = "Corporate actions executed outside the statutory objects clause or legal scope of an incorporated entity, rendering contracts historically void at common law."
    ),
    GlossaryEntry(
        englishTerm = "Fiduciary Duty of Directors",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Udindo wokhulupirika umene ma directors ali nawo osayika phindu lawo patsogolo pa kampani",
        academicTranslation = "The highest equitable standard of care requiring commercial officers and directors to act in uberrimae fidei (utmost good faith), avoiding unauthorized secret profits."
    ),
    GlossaryEntry(
        englishTerm = "Caveat Emptor (Buyer Beware)",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Wogula adziwe yekha za katundu asanagule; wogulitsa alibe udindo wouza zofooka zonse pokhapokha ngati ananamiza",
        academicTranslation = "Commercial rule under the Sale of Goods Act charging purchasers with inspecting items for patent defects prior to sale, absent express warranty or fraudulent concealment."
    ),
    GlossaryEntry(
        englishTerm = "Liquidated Damages vs. Penalty Clause",
        moduleCode = "BLaw",
        moduleName = "Business Law",
        spokenChichewaMetaphor = "Ndalama zoyembekezeka zowonongeka pamaso pa lamulo, osati chindapusa choopsa choononga mnzako",
        academicTranslation = "A genuine pre-estimate of loss sustained from contractual breach is enforceable; an extravagant sum intended in terrorem to compel performance constitutes an unenforceable penalty (Dunlop Pneumatic)."
    ),

    // === ECONOMICS ===
    GlossaryEntry(
        englishTerm = "Monetary Transmission Mechanism",
        moduleCode = "ECON",
        moduleName = "Economics",
        spokenChichewaMetaphor = "Njira imene ndalama ya boma (RBM) ikasinthidwa imagwirira ntchito kufikira ku misika yathu",
        academicTranslation = "The macroeconomic pathway through which central bank base policy rate adjustments alter commercial banking liquidity, credit issuance, and consumer price levels."
    ),
    GlossaryEntry(
        englishTerm = "Open Market Operations (OMO)",
        moduleCode = "ECON",
        moduleName = "Economics",
        spokenChichewaMetaphor = "Kugulitsana kwa ma Treasury Bills pakati pa Reserve Bank ndi mabanki am'manja kuti ndalama zambiri zosafunikira zitengedwe m'dongosolo",
        academicTranslation = "The programmatic sale and repurchase of sovereign treasury bills by the central bank to sterilize commercial excess liquidity and anchor inflationary expectations."
    ),
    GlossaryEntry(
        englishTerm = "Cash Reserve Ratio (CRR)",
        moduleCode = "ECON",
        moduleName = "Economics",
        spokenChichewaMetaphor = "Gawo la ndalama za anthu zomwe mabanki akuyenera kusiya ku Central Bank osazikongoletsa",
        academicTranslation = "Statutory liquidity threshold prescribed by the central bank dictating the minimum percentage of total customer liabilities commercial banks must deposit interest-free."
    ),

    // === COMPUTER SCIENCE ===
    GlossaryEntry(
        englishTerm = "Hash Table Collision",
        moduleCode = "CS",
        moduleName = "Computer Science",
        spokenChichewaMetaphor = "M'pamene zinthu ziwiri zosiyana zaikidwa m'bokosi limodzi la makalata ku post office",
        academicTranslation = "The scenario in associative mapping where two disparate keys compute to the identical array slot index via the mathematical hash function h(k)."
    ),
    GlossaryEntry(
        englishTerm = "Collision Resolution by Chaining",
        moduleCode = "CS",
        moduleName = "Computer Science",
        spokenChichewaMetaphor = "Kumangirira thumba kapena chingwe pa khomo la bokosi la makalata kuti lidzaze makalata owonjezera",
        academicTranslation = "Mitigating hash collision by maintaining an auxiliary linked list data structure originating from each indexed bucket, bounding retrieval to O(1 + α)."
    ),
    GlossaryEntry(
        englishTerm = "Open Addressing (Linear Probing)",
        moduleCode = "CS",
        moduleName = "Computer Science",
        spokenChichewaMetaphor = "Bokosi 45 likadzaza, kupita patsogolo pa bokosi 46 kapena 47 kukafuna malo opanda kanthu",
        academicTranslation = "Resolving collisions by storing all key-value entries directly within the array table, searching sequential slot intervals via probing sequences."
    ),

    // === PUBLIC HEALTH ===
    GlossaryEntry(
        englishTerm = "Indoor Residual Spraying (IRS) & Herd Protection",
        moduleCode = "PUB",
        moduleName = "Public Health",
        spokenChichewaMetaphor = "Mpanda wachitetezo wa mankhwala m'nyumba za mudzi wonse umene umagwa ngati ena akana kupopera",
        academicTranslation = "Applying residual insecticide formulations on residential interiors to suppress vector life expectancy; collective mortality protects non-sprayed individuals once coverage surpasses 85%."
    ),
    GlossaryEntry(
        englishTerm = "Entomological Inoculation Rate (EIR)",
        moduleCode = "PUB",
        moduleName = "Public Health",
        spokenChichewaMetaphor = "Chiwerengero cha kulumidwa ndi udzudzu wokhala ndi tizilombo ta malungo mwa munthu pa chaka chimodzi",
        academicTranslation = "The gold standard epidemiological measure of vector-borne malaria transmission, defined as the expected number of infectious mosquito bites per individual per unit of time."
    ),

    // === PEDAGOGY ===
    GlossaryEntry(
        englishTerm = "Code-Switching (Academic)",
        moduleCode = "PED",
        moduleName = "Pedagogy",
        spokenChichewaMetaphor = "Kulankhula Chisungu ndi Chichewa nthawi imodzi pophunzitsa kuti mfundo yovuta imveke bwino",
        academicTranslation = "Alternating between English and vernacular Chichewa during pedagogical instruction to bridge conceptual complexity and anchor foreign academic frameworks in indigenous mental schemas."
    )
)

@Composable
fun GlossaryScreen(
    initialModule: String = "ALL",
    modifier: Modifier = Modifier
) {
    var selectedModule by remember { mutableStateOf(initialModule) }
    var filterQuery by remember { mutableStateOf("") }

    val modules = listOf(
        Pair("ALL", "All Modules"),
        Pair("BLaw", "BLaw (Business Law)"),
        Pair("ECON", "Economics"),
        Pair("CS", "Computer Science"),
        Pair("PUB", "Public Health"),
        Pair("PED", "Pedagogy")
    )

    val filtered = remember(selectedModule, filterQuery) {
        academicGlossaryEntries.filter { entry ->
            val matchesModule = selectedModule == "ALL" || entry.moduleCode.equals(selectedModule, ignoreCase = true)
            val matchesQuery = filterQuery.isBlank() || (
                entry.englishTerm.contains(filterQuery, ignoreCase = true) ||
                entry.moduleName.contains(filterQuery, ignoreCase = true) ||
                entry.spokenChichewaMetaphor.contains(filterQuery, ignoreCase = true) ||
                entry.academicTranslation.contains(filterQuery, ignoreCase = true)
            )
            matchesModule && matchesQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = null,
                tint = Gold600,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = "Chichewa-Academic Translation Glossary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Filter by University Module or search legal & academic terms",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Module Filter Chips (Allows clicking BLaw to show terms concerning BLaw!)
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "Filter by Module / Course:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                modules.forEach { (code, label) ->
                    val isSelected = selectedModule == code
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedModule = code },
                        label = { Text(label) },
                        leadingIcon = if (code == "BLaw") {
                            { Icon(Icons.Default.Gavel, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        } else null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.testTag("filter_glossary_${code.lowercase()}")
                    )
                }
            }
        }

        // Search Input
        OutlinedTextField(
            value = filterQuery,
            onValueChange = { filterQuery = it },
            placeholder = { Text("Search specific term, Latin maxim, or Chichewa phrase...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = if (filterQuery.isNotBlank()) {
                {
                    IconButton(onClick = { filterQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_glossary_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        // Count indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Showing ${filtered.size} terms ${if (selectedModule != "ALL") "in $selectedModule" else ""}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (selectedModule != "ALL" || filterQuery.isNotBlank()) {
                Text(
                    text = "Reset Filters",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.testTag("reset_glossary_filters")
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filtered, key = { it.englishTerm }) { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = entry.englishTerm,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Surface(
                                color = if (entry.moduleCode == "BLaw") MaterialTheme.colorScheme.tertiaryContainer
                                else MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = entry.moduleName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (entry.moduleCode == "BLaw") MaterialTheme.colorScheme.onTertiaryContainer
                                    else MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        // Spoken Chichewa
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "🇲🇼 Chichewa Metaphor / Spoken Context: \"${entry.spokenChichewaMetaphor}\"",
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        // Academic Translation
                        Text(
                            text = entry.academicTranslation,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}
