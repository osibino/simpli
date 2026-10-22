package com.example.service

data class LecturePreset(
    val id: String,
    val institution: String,
    val courseName: String,
    val topicTitle: String,
    val lectureDate: String,
    val handoutExcerpts: String,
    val rawTranscript: String
)

object MalawiPresets {
    val presets = listOf(
        LecturePreset(
            id = "unima_econ_201",
            institution = "University of Malawi (UNIMA)",
            courseName = "ECON 201: Macroeconomics II",
            topicTitle = "Monetary Transmission Mechanism & RBM Policy",
            lectureDate = "2026-10-18 (Session 4)",
            handoutExcerpts = """
COURSE HANDOUT: ECON 201 - INTERMEDIATE MACROECONOMICS
Department of Economics, Chancellor College, University of Malawi
Module 4: Central Banking and Monetary Transmission
Reference: Blanchard, O. (2021) Macroeconomics (8th ed.), Chapter 14; Reserve Bank of Malawi Act 2018, Section 4.2.

1. Core Definition:
The Monetary Transmission Mechanism designates the structural channels through which changes in central bank policy instruments (Policy Rate, Cash Reserve Ratio, Open Market Operations) propagate through commercial bank balance sheets, interbank lending, and exchange rates to alter aggregate demand and general price level.

2. Reserve Bank of Malawi (RBM) Primary Instruments:
- Policy Rate (PR): The benchmark interest rate at which the RBM provides overnight liquidity to commercial banking institutions.
- Cash Reserve Ratio (CRR): Statutory liquidity reserve percentage that commercial institutions must maintain with the central bank without interest.
- Open Market Operations (OMO): Secondary market auctions of Treasury Bills and RBM securities to sterilize or inject commercial system reserves.

3. Structural Transmission Channels in Malawi:
- Bank Lending Channel: Policy rate variations alter prime lending rates, expanding or constricting private sector credit.
- Exchange Rate Channel: Differential between domestic policy rate and foreign rates influences foreign currency demand and import price pass-through.
- Liquidity Sterilization: Absorbing excess liquidity to prevent demand-pull inflationary pressure.
            """.trimIndent(),
            rawTranscript = """
[CHECKPOINT - 00:00] Good morning class. Welcome to Economics 201. Today we are continuing our discussion on the Reserve Bank of Malawi and the monetary transmission mechanism. Please have your Blanchard textbook open at Chapter 14 and look at section 4.2 of the department handout.

[CHECKPOINT - 08:30] Now, when RBM adjusts the Policy Rate, what actually happens? Um, you see, commercial banks like National Bank or Standard Bank borrow or settle balances against the central bank. If RBM raises the policy rate, cost of funds goes up.
Ndiye pamenepa, mukapita ku msika ku Tsoka kapena ku Limbe, mtengo wa chimanga ukakwera, simuti anthu akulakwitsa ayi. Chimene chikuchitika n'choti mabanki akakweza chiwongoladzanja, amalonda omwe anatenga ngongole amakakamizika kukweza mitengo ya katundu kuti athe kubweza ngongoleyo. Choncho ndalama ikasoŵa m'manja mwa anthu, zofuna kugula zimatsika, ndipo pamapeto pake mitengo imayamba kukhazikika.

[CHECKPOINT - 21:15] So that is the interest rate channel directly altering commercial borrowing. Now let's examine the Cash Reserve Ratio, the CRR, and Open Market Operations.
Tiyeni tione apa: OMO kapena Open Market Operations si msika wamba wamalonda wopita ndi basiketi ayi. Ndi pamene Reserve Bank imagulitsa kapena kugula ma Treasury Bills kuchokera ku mabanki. RBM ikafuna kutulutsa ndalama zochuluka zomwe zili m'manja mwa mabanki (excess liquidity) kuti zisachititse inflation, imagulitsa ma T-Bills amenewa ndi chiwongoladzanja chokopa. Mabanki amati, "Bwino tigule ma bills a boma m'malo mongopereka ngongole zosatsimikizika."

[CHECKPOINT - 35:40] To conclude today's session, observe how Blanchard notes in Chapter 14 page 312 that in an import-dependent economy, foreign exchange pass-through can overwhelm domestic rate hikes. For the upcoming midterm exam, be very certain to understand how OMO controls the money supply M2, and be prepared to derive the liquidity multiplier.
            """.trimIndent()
        ),
        LecturePreset(
            id = "mubas_cs_310",
            institution = "Malawi Univ. of Business & Applied Sciences (MUBAS)",
            courseName = "CS 310: Data Structures & Algorithms",
            topicTitle = "Hash Tables & Collision Resolution Strategies",
            lectureDate = "2026-10-15 (Session 6)",
            handoutExcerpts = """
MUBAS FACULTY OF COMPUTING: CS 310 COURSE GUIDE
Unit 3: Hash Tables and Associative Arrays
Reference: Cormen, Leiserson, Rivest, Stein (CLRS), Introduction to Algorithms (4th ed.), Chapter 11, pp. 272-289.

1. Direct Addressing vs Hash Tables:
Direct addressing requires an array slot for each conceivable key, leading to O(1) worst-case time but impractical O(|U|) memory waste. Hash tables map universe U into m slots via hash function h(k).

2. Collision Resolution Techniques:
- Chaining: Keys hashing to the same slot are stored in a linked list. Average search time is O(1 + alpha), where alpha = n/m is the load factor.
- Open Addressing: All elements occupy the table directly; collisions are resolved via probing sequences (linear probing, quadratic probing, double hashing).

3. Desirable Hash Function Properties:
Uniform distribution, determinism, and minimal clustering.
            """.trimIndent(),
            rawTranscript = """
[CHECKPOINT - 00:00] Afternoon everyone. In CS 310 today, we are diving deep into Chapter 11 of CLRS on Hash Tables and collision resolution. Turn to page 272.

[CHECKPOINT - 09:45] What happens when two distinct keys yield the exact same hash index? That is what we call a collision.
Tiyerekeze kuti muli ndi bokosi la makalata ku post office ya ku Blantyre kapena GPO ku Lilongwe. Ngati anthu awiri omwe ali ndi maina osiyana—mwachitsanzo a Phiri ndi a Banda—onse makalata awo aikidwa m'bokosi limodzi la nambala 45, pamenepo pali collision. Mu Chaining, pakhomo pa bokosilo timangomangirizapo chingwe ndi chikwama china cholandirira makalata owonjezera. Koma mu Open Addressing, a Banda akayang'ana bokosi 45 napeza lili ndi makalata a Phiri, amapita pa bokosi lotsatira la 46 kufunafuna malo opanda kanthu.

[CHECKPOINT - 24:30] That illustrates chaining versus linear probing vividly. Notice how the load factor alpha dictates your retrieval cost.
Mukalola kuti chiwerengero cha zinthu chidutse kukula kwa array (pamene alpha yapitilira 1), table yonse imayamba kulemera ndipo speed imatsika kwambiri kuchoka pa O(1) kufika pa O(n).

[CHECKPOINT - 38:00] Check your tutorial sheet question 3 for the midterm: prove that double hashing avoids primary and secondary clustering, and calculate the expected probes for an unsuccessful search under simple uniform hashing.
            """.trimIndent()
        ),
        LecturePreset(
            id = "must_pub_204",
            institution = "Malawi Univ. of Science & Technology (MUST)",
            courseName = "PUB 204: Epidemiology & Vector Control",
            topicTitle = "Malaria Vector Dynamics & Indoor Residual Spraying (IRS)",
            lectureDate = "2026-10-12 (Session 3)",
            handoutExcerpts = """
MUST INSTITUTE OF HEALTH & MEDICAL SCIENCES: PUB 204
Module 2: Vector Ecology and Transmission Dynamics of Plasmodium falciparum
Textbook: WHO Guidelines for Malaria Vector Control (2022); Malawi National Malaria Strategic Plan (2023-2030), Chapter 3.

1. Vector Transmission in Malawi:
Primary vectors: Anopheles funestus s.s. and Anopheles gambiae s.s. Transmission peaks following seasonal rainfall (December to April) across lake shore and Shire Valley lowlands.

2. Indoor Residual Spraying (IRS):
Application of residual insecticides (such as pirimiphos-methyl organophosphates and clothianidin neonicotinoids) to interior walls and ceilings where endophilic female mosquitoes rest post-bloodmeal.

3. Insecticide Resistance & Community Compliance:
Pyrethroid resistance monitoring (Kdr mutations and metabolic cytochrome P450 upregulation). High community acceptance (>85% coverage) is imperative to achieve community-level herd protection.
            """.trimIndent(),
            rawTranscript = """
[CHECKPOINT - 00:00] Good morning colleagues. In Epidemiology 204 today, we examine vector intervention strategies in high transmission zones like Chikwawa, Nsanje, and Nkhotakota. Reference chapter 3 of the Malawi National Malaria Strategic Plan.

[CHECKPOINT - 11:20] Why did the Ministry transition away from standard pyrethroids toward pirimiphos-methyl formulations for IRS? Because Anopheles funestus developed intense metabolic resistance.
Koma vuto lalikulu lomwe timakumana nalo m'madera ngati ku Nsanje ndi Chikwawa siliri pa sayansi ya mankhwala okha ayi; lili pa maganizo a anthu m'midzi. Anthu ena amati mankhwala a IRS akapoperedwa m'nyumba amadzutsa nsikidzi kapena fungo lake limabweretsa chifuwa, choncho amakaniza ogwira ntchito za umoyo kulowa m'nyumba zawo. Akatelo, mpanda wachitetezo wa mudzi wonse (community herd protection) umasweka, ndipo udzudzu umapeza pothawira m'nyumba zosapoperedwazo.

[CHECKPOINT - 26:10] So public health communication is as decisive as insecticide efficacy. When community coverage drops below 85 percent, the communal vector mortality effect collapses.
[CHECKPOINT - 39:00] For your continuous assessment test: be ready to explain the epidemiological significance of the Entomological Inoculation Rate (EIR) and the operational difference between LLIN mass distribution and targeted IRS campaigns.
            """.trimIndent()
        ),
        LecturePreset(
            id = "unima_law_101",
            institution = "University of Malawi (Faculty of Law)",
            courseName = "LAW 101: Law of Contract I",
            topicTitle = "Doctrine of Consideration & Privity of Contract",
            lectureDate = "2026-10-09 (Session 5)",
            handoutExcerpts = """
UNIMA FACULTY OF LAW: LAW 101 CONTRACT LAW
Unit 2: Formation of Contract - The Doctrine of Consideration
Authorities: Chitty on Contracts (34th ed.), Vol 1; Kamwendo v Reserve Bank of Malawi [2008] MLR 42; Stilk v Myrick (1809); Williams v Roffey Bros [1991].

1. Definition of Consideration:
Currie v Misa (1875): "A valuable consideration, in the sense of the law, may consist either in some right, interest, profit, or benefit accruing to the one party, or some forbearance, detriment, loss, or responsibility, given, suffered, or undertaken by the other."

2. Rules of Consideration:
- Consideration must be sufficient but need not be adequate (Thomas v Thomas).
- Past consideration is no consideration (Roscorla v Thomas), subject to the exception in Pao On v Lau Yiu Long.
- Performance of an existing public or contractual duty does not constitute good consideration unless practical commercial benefit is conferred (Williams v Roffey Bros).
            """.trimIndent(),
            rawTranscript = """
[CHECKPOINT - 00:00] Silence please. Welcome to Contract Law 101. Today we scrutinize the bedrock doctrine of English common law and Malawian jurisprudence: Consideration. Turn to your Chitty reading pack and note Kamwendo v Reserve Bank of Malawi.

[CHECKPOINT - 10:15] Consideration is the price for which the promise of the other is bought. It must move from the promisee.
Tiyeni tipereke chitsanzo cha moyo wathu watsiku ndi tsiku kuno ku Malawi: Mukakhala pabanja, ukamuuza mchemwali wako kuti, "Chifukwa unandisamala bwino chaka chatha pamene ndinali kudwala, ndidzakugulira galimoto mwezi wamawa," pamenepo palibe mgwirizano womwe bwalo lamilandu lingakakamize ayi. Chifukwa chiyani? Chisamalirocho chinachitika kale m'mbuyo—ndiye chimatchedwa Past Consideration. M'malamulo a contract, lonjezo logona pa zabwino zakale silimapereka ufulu wokasuma ku court pokhapokha panali pempho loyambirira ndi chiyembekezo cholipidwa.

[CHECKPOINT - 23:40] Now compare that with sufficiency versus adequacy. If you sell your maize mill worth five million kwacha for just one hundred kwacha, the law will not interfere with your bad bargain.
Bwalo lamilandu silisamala ngati mtengo woperekedwawo ndi wotsika modabwitsa; bola ngati pali chinthu chamtengo wapatali m'maso mwa lamulo ngakhale chikakhale chochepa, consideration ilipo.

[CHECKPOINT - 36:50] Memorize the ratio decidendi in Williams v Roffey Brothers regarding practical commercial benefit, and contrast it with Foakes v Beer on part-payment of debt for your terminal examination.
            """.trimIndent()
        )
    )
}
