package com.moviebuffs.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.moviebuffs.R;
import com.moviebuffs.adapters.TicketAdapter;

public class TicketRemoveDialogFragment extends DialogFragment {

    TicketAdapter TicketAdapter;

    Button okButton;
    Button cancelButton;
    AlertDialog dialog;

    int adapter_position;


    private final int mBackgroundBlurRadius = 80;
    private final int mBlurBehindRadius = 20;

    // We set a different dim amount depending on whether window blur is enabled or disabled
    private final float mDimAmountWithBlur = 0.1f;
    private final float mDimAmountNoBlur = 0.4f;

    // We set a different alpha depending on whether window blur is enabled or disabled
    private final int mWindowBackgroundAlphaWithBlur = 170;
    private final int mWindowBackgroundAlphaNoBlur = 255;

    // Use a rectangular shape drawable for the window background. The outline of this drawable
    // dictates the shape and rounded corners for the window background blur area.
    private Drawable mWindowBackgroundDrawable;
    public TicketRemoveDialogFragment(TicketAdapter ticketAdapter, int adapter_position){
        this.TicketAdapter = ticketAdapter;
        this.adapter_position = adapter_position;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
////        productsInfo = myDbHandler.allProducts();
//        View view = inflater.inflate(R.layout.fragment_ticket_remove_dialog, container, false);
//        okButton = view.findViewById(R.id.dialog_ok_button);
//        cancelButton = view.findViewById(R.id.dialog_cancel_button);
//        return view;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ticket_remove_dialog, null);


        okButton = view.findViewById(R.id.dialog_ok_button);
        cancelButton = view.findViewById(R.id.dialog_cancel_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketAdapter.updateTicketList(v, adapter_position);
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//R.style.MyDialogTheme
        builder.setView(view);
//        builder.setCancelable(false);
        dialog = builder.create();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        mWindowBackgroundDrawable = getDrawable(R.drawable.window_background);
//        dialog.getWindow().setBackgroundDrawable(mWindowBackgroundDrawable);

        mWindowBackgroundDrawable = getResources().getDrawable(R.drawable.tickets, getContext().getTheme());
        dialog.getWindow().setBackgroundDrawable(mWindowBackgroundDrawable);

//        if (buildIsAtLeastS()) {
//            // Enable blur behind. This can also be done in xml with R.attr#windowBlurBehindEnabled
//            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//
//            // Register a listener to adjust window UI whenever window blurs are enabled/disabled
//            setupWindowBlurListener();
//        } else {
//            // Window blurs are not available prior to Android S
//            updateWindowForBlurs(false /* blursEnabled */);
//        }
//
//        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
        return dialog;
    }

//    @RequiresApi(api = Build.VERSION_CODES.S)
//    private void setupWindowBlurListener() {
//        Consumer<Boolean> windowBlurEnabledListener = this::updateWindowForBlurs;
//        dialog.getWindow().getDecorView().addOnAttachStateChangeListener(
//                new View.OnAttachStateChangeListener() {
//                    @Override
//                    public void onViewAttachedToWindow(View v) {
//                        getActivity().getWindowManager().addCrossWindowBlurEnabledListener(
//                                windowBlurEnabledListener);
//                    }
//
//                    @Override
//                    public void onViewDetachedFromWindow(View v) {
//                        getActivity().getWindowManager().removeCrossWindowBlurEnabledListener(
//                                windowBlurEnabledListener);
//                    }
//                });
//    }
//
//    private void updateWindowForBlurs(boolean blursEnabled) {
//        mWindowBackgroundDrawable.setAlpha(blursEnabled && mBackgroundBlurRadius > 0 ?
//                mWindowBackgroundAlphaWithBlur : mWindowBackgroundAlphaNoBlur);
//        dialog.getWindow().setDimAmount(blursEnabled && mBlurBehindRadius > 0 ?
//                mDimAmountWithBlur : mDimAmountNoBlur);
//
//        if (buildIsAtLeastS()) {
//            // Set the window background blur and blur behind radii
////            dialog.getWindow().setBackgroundBlurRadius(mBackgroundBlurRadius);
////            dialog.getWindow().getAttributes().setBlurBehindRadius(mBlurBehindRadius);
//            dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
//        }
//    }
//
//    private static boolean buildIsAtLeastS() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
//    }
    @Override
    public void onStart() {
        super.onStart();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // расстягивает диалоговое окно на весь экран
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            // Enable blur behind. This can also be done in xml with R.attr#windowBlurBehindEnabled
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//            // Set the window background blur and blur behind radii
//            dialog.getWindow().setBackgroundBlurRadius(50);
//            dialog.getWindow().getAttributes().setBlurBehindRadius(20);
//            dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
//        }
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////        builder.setView(R.layout.fragment_ticket_remove_dialog);
//        AlertDialog dialog = builder.create();
//
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TicketAdapter.updateTicketList();
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////        builder.setView(R.layout.fragment_ticket_remove_dialog);
////        builder.setTitle("Хотите удалить билет?")
////                .setMessage("Вы уверены, что хотите удалить этот билет?")
////                .setPositiveButton("Хочу", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        // Удаление элемента из списка
////                        TicketAdapter.updateTicketList();
////                    }
////                })
////                .setNegativeButton("Не хочу", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int id) {
////                        // Отмена удаления элемента
////                        dismiss();
////                    }
////                });
//        return dialog;
//    }
}
