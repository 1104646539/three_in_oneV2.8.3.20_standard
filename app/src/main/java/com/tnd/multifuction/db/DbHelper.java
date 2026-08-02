package com.tnd.multifuction.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tnd.jinbiao.model.CardCompanyModel;
import com.tnd.jinbiao.model.LineModel;
import com.tnd.jinbiao.model.PeopleModel;
import com.tnd.jinbiao.model.ResultModel;
import com.tnd.jinbiao.model.ResultPhotoImgModel;
import com.tnd.jinbiao.model.SampleModel;
import com.tnd.jinbiao.model.SampleTypeModel;
import com.tnd.jinbiao.model.SampleUnitModel;
import com.tnd.jinbiao.model.ShiJiModel;
import com.tnd.multifuction.model.Project;
import com.tnd.multifuction.util.Global;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** Database initialization and V4.0 preset migration. */
public class DbHelper {

    private static DbUtils db;
    private static Context context;

    private DbHelper() {
    }

    public static DbUtils GetInstance() {
        return db;
    }

    public static void InitDb(Context ctx) {
        context = ctx;
        File file = context.getDatabasePath(Global.DATA_BASE_NAME);
        final boolean isExistingDatabase = file.exists();
        db = DbUtils.create(context, Global.DATA_BASE_NAME, Global.DATABASE_VERSION,
                new DbUtils.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbUtils dbUtils, int oldVersion, int newVersion) {
                        if (oldVersion < 2 && newVersion >= 2) {
                            try {
                                createPresetTables(dbUtils);
                                replacePresetData(dbUtils);
                            } catch (DbException e) {
                                throw new IllegalStateException("V4.0预置数据升级失败", e);
                            }
                        }
                    }
                });

        db.configAllowTransaction(true);
        if (!isExistingDatabase) {
            try {
                initNewDatabase(db);
            } catch (DbException e) {
                throw new IllegalStateException("数据库初始化失败", e);
            }
        }
    }

    private static void initNewDatabase(DbUtils dbUtils) throws DbException {
        dbUtils.createTableIfNotExist(ResultModel.class);
        dbUtils.createTableIfNotExist(PeopleModel.class);
        dbUtils.createTableIfNotExist(ResultPhotoImgModel.class);
        dbUtils.createTableIfNotExist(SampleUnitModel.class);
        dbUtils.createTableIfNotExist(ShiJiModel.class);
        createPresetTables(dbUtils);
        replacePresetData(dbUtils);

        dbUtils.save(new PeopleModel("检测站", 1));
        dbUtils.save(new PeopleModel("admin", 2));
        ShiJiModel reagentCompany = new ShiJiModel();
        reagentCompany.code = "";
        reagentCompany.name = "浩景";
        dbUtils.save(reagentCompany);
    }

    private static void createPresetTables(DbUtils dbUtils) throws DbException {
        dbUtils.createTableIfNotExist(Project.class);
        dbUtils.createTableIfNotExist(LineModel.class);
        dbUtils.createTableIfNotExist(CardCompanyModel.class);
        dbUtils.createTableIfNotExist(SampleModel.class);
        dbUtils.createTableIfNotExist(SampleTypeModel.class);
    }

    /** Replaces user-edited preset rows with the approved V4.0 factory values. */
    private static void replacePresetData(DbUtils dbUtils) throws DbException {
        SQLiteDatabase database = dbUtils.getDatabase();
        dbUtils.configAllowTransaction(false);
        database.beginTransaction();
        try {
            dbUtils.deleteAll(Project.class);
            dbUtils.deleteAll(LineModel.class);
            dbUtils.deleteAll(CardCompanyModel.class);
            dbUtils.deleteAll(SampleModel.class);
            dbUtils.deleteAll(SampleTypeModel.class);

            List<Project> projects = new ArrayList<>();
            for (PresetData.ProjectPreset preset : PresetData.PROJECTS) {
                projects.add(new Project("", preset.name, "GB/T 5009.199",
                        preset.detectionLimit, preset.k, preset.b, preset.wavelength, preset.unit));
            }
            dbUtils.saveAll(projects);

            CardCompanyModel company = new CardCompanyModel(PresetData.CARD_COMPANY,
                    PresetData.SCAN_START, PresetData.SCAN_END,
                    PresetData.CT_WIDTH, PresetData.CT_DISTANCE);
            dbUtils.save(company);

            List<LineModel> lines = new ArrayList<>();
            addLineProjects(lines, PresetData.DISAPPEARING_LINE_PROJECTS);
            addLineProjects(lines, PresetData.COMPARISON_LINE_PROJECTS);
            dbUtils.saveAll(lines);

            List<SampleTypeModel> sampleTypes = new ArrayList<>();
            for (String name : PresetData.SAMPLE_TYPES) {
                sampleTypes.add(new SampleTypeModel(name));
            }
            dbUtils.saveAll(sampleTypes);

            List<SampleModel> samples = new ArrayList<>();
            for (String name : PresetData.SAMPLES) {
                samples.add(new SampleModel(name));
            }
            dbUtils.saveAll(samples);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            dbUtils.configAllowTransaction(true);
        }
    }

    private static void addLineProjects(List<LineModel> target, PresetData.LinePreset[] presets) {
        for (PresetData.LinePreset preset : presets) {
            target.add(new LineModel(preset.source, preset.name, PresetData.CARD_COMPANY,
                    PresetData.SCAN_START, PresetData.SCAN_END,
                    PresetData.CT_WIDTH, PresetData.CT_DISTANCE,
                    "1", preset.threshold, "比值"));
        }
    }

    public static String getSampleNumber(String goodsName) {
        SQLiteDatabase database = db.getDatabase();
        if (database == null) {
            return null;
        }
        Cursor cursor = database.query("com_tnd_multifuction_model_SampleName", null,
                "sampleName = ?", new String[]{goodsName}, null, null, "", "");
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex("sampleNumber"));
            }
            return null;
        } finally {
            cursor.close();
        }
    }
}
