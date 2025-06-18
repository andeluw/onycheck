package com.project.onycheck.data

import com.project.onycheck.R

object DiseaseData {
    private val details = listOf(
        DiseaseDetail(
            key = "Acral_Lentiginous_Melanoma",
            userFriendlyName = "Acral Lentiginous Melanoma",
            imageRes = R.drawable.disease_melanoma,
            overview = "A rare and serious type of skin cancer that appears on the palms, soles, or under the nails. It often presents as a dark streak and can be mistaken for a bruise.",
            symptoms = listOf(
                "A dark brown or black streak under the nail, often starting at the cuticle.",
                "Discoloration that widens over time.",
                "Nail lifting, splitting, or cracking.",
                "Bleeding or a nodule under the nail."
            ),
            causes = listOf(
                "The exact cause is unknown and not linked to sun exposure like other melanomas.",
                "It is more common in individuals with darker skin tones.",
                "Genetic factors may play a role."
            ),
            nextSteps = listOf(
                "Urgent consultation with a dermatologist is highly recommended.",
                "A biopsy is required to confirm the diagnosis.",
                "Early detection is critical for a positive outcome."
            )
        ),
        DiseaseDetail(
            key = "Onychogryphosis",
            userFriendlyName = "Onychogryphosis",
            imageRes = R.drawable.correctnail,
            overview = "Commonly known as 'Ram's Horn Nails,' this condition involves a significant thickening and overgrowth of the nail, usually affecting the big toe. The nail becomes curved and discolored.",
            symptoms = listOf(
                "Thick, hard nails that are difficult to trim.",
                "A curved, claw-like or horn-like shape.",
                "Yellowish or brownish discoloration.",
                "Pain or discomfort, especially when wearing shoes."
            ),
            causes = listOf(
                "Often caused by long-term neglect or inability to care for nails (common in the elderly).",
                "Repetitive trauma or injury to the nail.",
                "Poor circulation (peripheral vascular disease) or conditions like psoriasis."
            ),
            nextSteps = listOf(
                "Consult with a podiatrist or dermatologist for proper trimming and management.",
                "Treatment may involve softening the nail and regular professional care.",
                "Addressing the underlying cause is important."
            )
        ),
        DiseaseDetail(
            key = "blue_finger",
            userFriendlyName = "Blue Finger",
            imageRes = R.drawable.correctnail,
            overview = "Blue discoloration of the nails (cyanosis) indicates a lack of oxygen in the red blood cells. It can be a sign of an underlying health issue affecting the respiratory or cardiovascular system.",
            symptoms = listOf(
                "A noticeable blue or purplish tint to the nail bed.",
                "Often accompanied by coldness in the fingers or toes.",
                "May be associated with shortness of breath or dizziness."
            ),
            causes = listOf(
                "Cold temperatures constricting blood vessels.",
                "Lung and breathing problems like asthma or COPD.",
                "Heart conditions or circulation issues.",
                "Certain medications or exposure to silver salts."
            ),
            nextSteps = listOf(
                "If persistent and not related to cold, seek medical attention promptly.",
                "A doctor will investigate the underlying cause, which may involve blood tests or imaging.",
                "Treatment is focused on the underlying medical condition."
            )
        ),
        DiseaseDetail(
            key = "clubbing",
            userFriendlyName = "Nail Clubbing",
            imageRes = R.drawable.disease_clubbing,
            overview = "Nail clubbing is when the fingertips enlarge and the nails curve around them. This change often happens gradually over years and can be a sign of a serious underlying medical condition.",
            symptoms = listOf(
                "Widening and rounding of the fingertips.",
                "Downward curving of the nails.",
                "The nail bed may feel soft or spongy.",
                "The angle between the cuticle and the nail base increases."
            ),
            causes = listOf(
                "Most commonly associated with lung diseases (like lung cancer or cystic fibrosis).",
                "Cardiovascular issues, such as congenital heart defects.",
                "Gastrointestinal problems like inflammatory bowel disease.",
                "Can sometimes be a harmless, inherited trait."
            ),
            nextSteps = listOf(
                "Consult a healthcare provider immediately to investigate the underlying cause.",
                "Diagnostic tests may include chest X-rays, CT scans, and blood tests.",
                "Treatment targets the underlying disease causing the clubbing."
            )
        ),
        DiseaseDetail(
            key = "pitting",
            userFriendlyName = "Nail Pitting",
            imageRes = R.drawable.disease_pitting,
            overview = "Nail pitting refers to the presence of small, shallow depressions or 'pits' on the surface of the nails. It is often associated with certain autoimmune and skin conditions.",
            symptoms = listOf(
                "Small, ice pick-like depressions on the nail plate.",
                "The number of pits can vary from one to dozens.",
                "Nails may also show discoloration or abnormal thickness."
            ),
            causes = listOf(
                "Most commonly linked to Psoriasis (psoriatic nails).",
                "Can also be related to alopecia areata, an autoimmune disease causing hair loss.",
                "Connective tissue disorders like Reiter's syndrome."
            ),
            nextSteps = listOf(
                "A visit to a dermatologist is recommended to diagnose the cause.",
                "Treatment often focuses on managing the underlying condition (e.g., psoriasis treatment).",
                "Topical treatments or injections may be used in some cases."
            )
        ),
    )

    fun findDisease(key: String): DiseaseDetail? {
        return details.find { it.key == key }
    }
}