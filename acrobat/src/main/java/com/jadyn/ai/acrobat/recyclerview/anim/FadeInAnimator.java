//package com.jadyn.ai.kotlin_d.function.ui.recyclerview.anim;
//
//import android.support.v4.view.ViewCompat;
//import android.support.v7.widget.RecyclerView;
//import android.view.animation.Interpolator;
//
//public class FadeInAnimator extends BaseItemAnimator {
//
//    public FadeInAnimator() {
//    }
//
//    public FadeInAnimator(Interpolator interpolator) {
//        setMInterpolator(interpolator);
//    }
//
//    @Override
//    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
//        ViewCompat.animate(holder.itemView)
//                .alpha(0)
//                .setDuration(getRemoveDuration())
//                .setInterpolator(getMInterpolator())
//                .setListener(new DefaultRemoveVpaListener(holder))
//                .setStartDelay(getRemoveDelay(holder))
//                .start();
//    }
//
//    @Override
//    protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
//        ViewCompat.setAlpha(holder.itemView, 0);
//    }
//
//    @Override
//    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
//        ViewCompat.animate(holder.itemView)
//                .alpha(1)
//                .setDuration(getAddDuration())
//                .setInterpolator(getMInterpolator())
//                .setListener(new DefaultAddVpaListener(holder))
//                .setStartDelay(getAddDelay(holder))
//                .start();
//    }
//}
