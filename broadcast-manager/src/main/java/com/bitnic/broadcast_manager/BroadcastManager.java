package com.bitnic.broadcast_manager;

import static android.content.Context.RECEIVER_EXPORTED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Broadcast manager.
 */
public class BroadcastManager {

    /**
     * Limits the scope of transmitted packets
     */
    public static String broadcast_scope;
    private final static List<InnerWrapper> innerWrappers=new ArrayList<>();

    /**
     * Registers a receiver that will receive a message as a string
     * If a receiver with the same filter is already registered, registration will not occur
     *
     * @param context Application context
     * @param filter Receiver filter
     * @param action Actions to be executed upon receipt of data as a string
     */
    public static void add(Context context, String filter, IActionReceiver<String> action){

        for (InnerWrapper innerWrapper : innerWrappers) {
            if(innerWrapper.filter.equals(filter)){
                Log.e("BroadcastManager","Filter "+filter+", already used earlier during registration");
                return;
            }

        }


        InnerWrapper innerWrapper=new InnerWrapper();
        innerWrapper.context = context;
        innerWrapper.filter=filter;
        innerWrapper.filter_msg=filter+"_msg";
        innerWrapper.receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra(innerWrapper.filter_msg);
                if(action!=null){
                    action.invoke(message);
                }

            }
        };
        IntentFilter filterCommonMessage = new IntentFilter();
        filterCommonMessage.addAction(filter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                context.registerReceiver(innerWrapper.receiver,filterCommonMessage,RECEIVER_EXPORTED);

        }else{
            context.registerReceiver(innerWrapper.receiver,filterCommonMessage);
        }
        innerWrappers.add(innerWrapper);
    }
    /**
     * Register a receiver that will receive a message in the form of an object whose class implements the Parcelable interface.
     * If a receiver with the same filter has already been registered, registration will not occur.
     *
     * @param context Application context
     * @param filter Receiver filter
     * @param action Actions to be executed upon receipt of data from an object whose class implements the Parcelable interface.
     */
    public static <T extends Parcelable> void addParcelable(Context context, String filter, IActionReceiver<T> action){

        for (InnerWrapper innerWrapper : innerWrappers) {
            if(innerWrapper.filter.equals(filter)){
                Log.e("BroadcastManager","Filter "+filter+", already used earlier during registration");
                return;
            }
        }

        InnerWrapper innerWrapper=new InnerWrapper();
        innerWrapper.context = context;
        innerWrapper.filter=filter;
        innerWrapper.filter_msg=filter+"_msg";
        innerWrapper.receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                T message = intent.getParcelableExtra(innerWrapper.filter_msg);
                if(action!=null){
                    action.invoke(message);
                }

            }
        };
        IntentFilter filterCommonMessage = new IntentFilter();
        filterCommonMessage.addAction(filter);

//LocalBroadcastManager.getInstance(context).registerReceiver(innerWrapper.receiver,filterCommonMessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                context.registerReceiver(innerWrapper.receiver,filterCommonMessage,RECEIVER_EXPORTED);


        }else{
            context.registerReceiver(innerWrapper.receiver,filterCommonMessage);
        }
        innerWrappers.add(innerWrapper);
    }

    /**
     *Dispose.
     *
     * @param context Application context
     */
    public static void dispose(Context context){
        List<InnerWrapper> innerWrappersDelete=new ArrayList<>();
        for (InnerWrapper wrapper : innerWrappers) {
            if(wrapper.context == context){
                innerWrappersDelete.add(wrapper);
            }
        }
        for (InnerWrapper innerWrapper : innerWrappersDelete) {
            context.unregisterReceiver(innerWrapper.receiver);
        }
        BroadcastManager.innerWrappers.removeAll(innerWrappersDelete);
    }

    /**
     * Send a message to the receiver
     *
     @param context Application context
      * @param filter Receiver filter
     * @param msg Message body (string)
     */
    public static void sendMessage(Context context, String filter, String msg){
        if(context==null) return;
        Intent intent=new Intent(filter);
        intent.putExtra(filter+"_msg",msg);
        if(broadcast_scope!=null){
            intent.setPackage(broadcast_scope);
        }

        context.sendBroadcast(intent);
    }
    /**
     * Send a message to the receiver
     *
     @param context Application context
      * @param filter Receiver filter
     * @param ob Message body, an object whose class extends the Parcelable interface
     */
    public static void sendMessage(Context context, String filter, Parcelable ob){
        if(context==null) return;
        Intent intent=new Intent(filter);
        intent.putExtra(filter+"_msg",ob);
        if(broadcast_scope!=null){
            intent.setPackage(broadcast_scope );
        }
        context.sendBroadcast(intent);
    }


}
