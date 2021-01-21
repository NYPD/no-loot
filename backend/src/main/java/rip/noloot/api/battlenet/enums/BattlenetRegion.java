package rip.noloot.api.battlenet.enums;

public enum BattlenetRegion {

    CHINA("CN", "zh_CN"),
    USA("US", "en_US", "es_MX", "pt_BR"),
    EUROPE("EU", "en_GB", "es_ES", "fr_FR", "ru_RU", "de_DE", "pt_PT", "it_IT"),
    KOREA("KR", "ko_KR"),
    TAIWAN("TW", "zh_TW");

    private final String code;
    private final String[] locales;

    private BattlenetRegion(String code,
                            String... locales) {
        this.code = code;
        this.locales = locales;
    }

    public String getCode() {
        return this.code;
    }

    public String[] getLocales() {
        return this.locales;
    }

}
