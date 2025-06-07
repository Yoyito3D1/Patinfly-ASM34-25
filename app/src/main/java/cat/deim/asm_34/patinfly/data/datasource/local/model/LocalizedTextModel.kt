package cat.deim.asm_34.patinfly.data.datasource.local.model

import cat.deim.asm_34.patinfly.domain.models.LocalizedText

data class LocalizedTextModel(
    val text: String,
    val language: String
) {
    fun toDomain(): LocalizedText {
        return LocalizedText(text = text, language = language)
    }

    companion object {
        fun fromDomain(localized: LocalizedText): LocalizedTextModel {
            return LocalizedTextModel(text = localized.text, language = localized.language)
        }
    }
}
