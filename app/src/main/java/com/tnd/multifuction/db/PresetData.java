package com.tnd.multifuction.db;

/** V4.0 factory presets from the approved instrument preset document. */
public final class PresetData {

    public static final String PESTICIDE_PROJECT = "农药残留";
    public static final String CARD_COMPANY = "浩景A";
    public static final String SCAN_START = "300";
    public static final String SCAN_END = "940";
    public static final String CT_DISTANCE = "320";
    public static final String CT_WIDTH = "120";

    public static final ProjectPreset[] PROJECTS = {
            new ProjectPreset(PESTICIDE_PROJECT, 410, 0f, 1f, 0f, "%"),
            new ProjectPreset("甲醛", 410, 1.0f, 16.467f, -3.1276f, "mg/kg"),
            new ProjectPreset("吊白块", 410, 10.0f, 16.467f, -3.1276f, "mg/kg"),
            new ProjectPreset("亚硝酸盐", 535, 1.0f, 35.108f, -1.6583f, "mg/kg"),
            new ProjectPreset("二氧化硫", 410, 10.0f, 254.79f, -3.4248f, "mg/kg"),
            new ProjectPreset("双氧水", 410, 10.0f, 360.84f, -11.02f, "mg/kg"),
            new ProjectPreset("硼砂", 410, 5.0f, 106.07f, -5.30f, "mg/kg"),
            new ProjectPreset("甲醇", 410, 0.2f, 3.2993f, -0.0235f, "mg/kg"),
            new ProjectPreset("硫酸铝钾", 535, 2.0f, 251.18f, -13.11f, "mg/kg"),
            new ProjectPreset("重金属铅", 535, 0.2f, 13.94f, -1.15f, "mg/kg"),
            new ProjectPreset("山梨酸钾", 535, 20.0f, 2.97f, -0.02f, "mg/kg"),
            new ProjectPreset("溴酸钾", 535, 0.5f, 139.7f, -6.02f, "mg/kg"),
            new ProjectPreset("糖精钠", 590, 1f, 0.46f, -0.01f, "mg/kg"),
            new ProjectPreset("挥发性盐基氮", 590, 20.0f, 25.18f, -1.1f, "mg/kg"),
            new ProjectPreset("硝酸盐", 535, 0.7f, 10.7005f, -0.327f, "mg/kg"),
            new ProjectPreset("重金属铬", 535, 0.1f, 1.15f, 0.07f, "mg/kg"),
            new ProjectPreset("重金属镉", 535, 0.25f, 4.39f, -0.16f, "mg/kg"),
            new ProjectPreset("过氧化苯甲酰", 535, 0.09f, 0.79f, 0.05f, "mg/kg"),
            new ProjectPreset("谷氨酸钠", 410, 1f, 108f, -6f, "mg/kg"),
            new ProjectPreset("硫酸镁", 535, 0.09f, 0.79f, 0.05f, "mg/kg"),
            new ProjectPreset("甜蜜素", 535, 0.6f, 7.66f, 0.18f, "mg/kg")
    };

    public static final String[] SAMPLE_TYPES = {
            "新鲜肉类", "新鲜果蔬", "新鲜水产", "冻肉类", "腌制肉类", "冰鲜水产", "粮油类"
    };

    public static final String[] SAMPLES = {
            "猪肉", "牛肉", "羊肉", "鸡肉", "鸭肉", "鹅肉", "兔肉", "鱼", "虾", "蟹",
            "黄瓜", "豆芽", "草莓", "花椒", "八角", "辣椒干", "辣椒粉", "陈皮", "养殖水"
    };

    public static final LinePreset[] DISAPPEARING_LINE_PROJECTS = {
            new LinePreset("盐酸克伦特罗", 2, "0.1"), new LinePreset("莱克多巴胺", 2, "0.1"),
            new LinePreset("沙丁胺醇", 2, "0.1"), new LinePreset("灭蝇胺", 2, "0.1"),
            new LinePreset("吡虫啉", 2, "0.1"), new LinePreset("三聚氰胺", 2, "0.1"),
            new LinePreset("黄曲霉M1", 2, "0.1"), new LinePreset("罂粟壳", 2, "0.1"),
            new LinePreset("玉米赤霉烯酮", 2, "0.1"), new LinePreset("重金属铅", 2, "0.9")
    };

    public static final LinePreset[] COMPARISON_LINE_PROJECTS = {
            new LinePreset("喹诺酮", 3, "0.9"), new LinePreset("黄曲霉B1", 3, "0.9"),
            new LinePreset("腐霉利", 3, "0.9"), new LinePreset("呕吐毒素", 3, "0.9"),
            new LinePreset("罗丹明B", 3, "0.9"), new LinePreset("磺胺类", 3, "0.9"),
            new LinePreset("6-苄基腺嘌呤", 3, "0.9"), new LinePreset("氯霉素", 3, "0.9"),
            new LinePreset("孔雀石绿", 3, "0.9"), new LinePreset("呋喃唑酮", 3, "0.9"),
            new LinePreset("呋喃它酮", 3, "0.9"), new LinePreset("呋喃妥因", 3, "0.9"),
            new LinePreset("呋喃西林", 3, "0.9"), new LinePreset("克百威", 3, "0.9"),
            new LinePreset("噻虫胺", 3, "0.9"), new LinePreset("噻虫嗪", 3, "0.9"),
            new LinePreset("恩诺沙星", 3, "0.9"), new LinePreset("氟苯尼考", 3, "0.9"),
            new LinePreset("甲硝唑", 3, "0.9")
    };

    private PresetData() {
    }

    public static final class ProjectPreset {
        public final String name;
        public final int wavelength;
        public final float detectionLimit;
        public final float k;
        public final float b;
        public final String unit;

        ProjectPreset(String name, int wavelength, float detectionLimit,
                      float k, float b, String unit) {
            this.name = name;
            this.wavelength = wavelength;
            this.detectionLimit = detectionLimit;
            this.k = k;
            this.b = b;
            this.unit = unit;
        }
    }

    public static final class LinePreset {
        public final String name;
        public final int source;
        public final String threshold;

        LinePreset(String name, int source, String threshold) {
            this.name = name;
            this.source = source;
            this.threshold = threshold;
        }
    }
}
