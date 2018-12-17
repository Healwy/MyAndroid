package com.titan.platform.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.app.Application;
// TODO 2018.12.17 临时注释
//import android.service.persistentdata.PersistentDataBlockManager;

public class TvRestManagerAdapter {
    private Context mContext;
    private static final String SHUTDOWN_INTENT_EXTRA = "shutdown";

    public TvRestManagerAdapter(Context context) {
        this.mContext = context;
    }

    public void onFactoryReset(){
        // TODO 2018.12.17 临时注释
//        final PersistentDataBlockManager pdbManager = (PersistentDataBlockManager)
//                mContext.getSystemService(Context.PERSISTENT_DATA_BLOCK_SERVICE);
//        if (pdbManager != null && !pdbManager.getOemUnlockEnabled() &&
//                Settings.Global.getInt(mContext.getContentResolver(),
//                        Settings.Global.DEVICE_PROVISIONED, 0) != 0) {
//            pdbManager.wipe();
//            doMasterClear();
//        }else {
//            doMasterClear();
//        }
    }


    private void doMasterClear() {
        // TODO 2018.12.17 临时注释
//        Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR);
//        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//        intent.putExtra(Intent.EXTRA_REASON, "MasterClearConfirm");
//        intent.putExtra(Intent.EXTRA_WIPE_EXTERNAL_STORAGE, 0);
//        mContext.sendBroadcast(intent);
    }


    public void preFactoryReset(){

    }

    public void afterFactoryReset(){

    }

}
