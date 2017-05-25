package com.example.ido_klzujtc.realm_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eggheadgames.realmassethelper.IRealmAssetHelperStorageListener;
import com.eggheadgames.realmassethelper.RealmAssetHelper;
import com.eggheadgames.realmassethelper.RealmAssetHelperStatus;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init debug library
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                RealmInspectorModulesProvider.builder(this)
                                        .withLimit(100000)
                                        .build())
                        .build());

        Realm.init(getApplicationContext());
        RealmAssetHelper
                .getInstance(getApplicationContext())
                .loadDatabaseToStorage("data", "SuperupDB", new IRealmAssetHelperStorageListener() {
            //    .loadDatabaseToStorage("data", "watsons", new IRealmAssetHelperStorageListener() {
            @Override
            public void onLoadedToStorage(String realmDbName, RealmAssetHelperStatus status) {
                RealmConfiguration configuration = new RealmConfiguration.Builder()
                        .name(realmDbName)
                        .build();
                DynamicRealm realm = DynamicRealm.getInstance(configuration);

                for(RealmObjectSchema realmObjectSchema : ((RealmSchema)realm.getSchema()).getAll()) {
                    System.out.println("name:" +realmObjectSchema.getClassName());
                   // realmObjectSchema.getClassName();
                }
            }
        });
    }
}
