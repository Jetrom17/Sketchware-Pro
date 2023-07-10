package com.besome.sketch.lib.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Set;

import a.a.a.gB;

public abstract class CollapsibleViewHolder extends RecyclerView.ViewHolder {
    private boolean animateNextTransformation = false;

    /**
     * Descendants <em>MUST</em> call {@link #onDoneInitializingViews()} in the constructor after
     * initializing the view holder's views.
     */
    public CollapsibleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected final void onDoneInitializingViews() {
        for (var view : getOnClickCollapseTriggerViews()) {
            view.setOnClickListener(v -> {
                if (isCollapsed()) {
                    expand();
                } else {
                    collapse();
                }
            });
        }
        for (var view : getOnLongClickCollapseTriggerViews()) {
            view.setOnLongClickListener(v -> {
                if (isCollapsed()) {
                    expand();
                } else {
                    collapse();
                }
                return true;
            });
        }
    }

    protected abstract boolean isCollapsed();

    protected abstract void setIsCollapsed(boolean isCollapsed);

    /**
     * @return The view that should be hidden/"collapsed" when the RecyclerView item is collapsed.
     */
    @NonNull
    protected abstract ViewGroup getOptionsLayout();

    /**
     * @return Views that act like an arrow icon and collapse/expand the RecyclerView item on click.
     */
    @NonNull
    protected Set<? extends View> getOnClickCollapseTriggerViews() {
        return Collections.emptySet();
    }

    /**
     * @return Views that collapse/expand the RecyclerView on long click. Usually the main
     * RecyclerView item view / body that users can easily long click on.
     */
    @NonNull
    protected Set<? extends View> getOnLongClickCollapseTriggerViews() {
        return Collections.emptySet();
    }

    public void collapse() {
        setIsCollapsed(true);
        for (var v : getOnClickCollapseTriggerViews()) {
            gB.a(v, 0, null);
        }
        gB.a(getOptionsLayout(), 300, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getOptionsLayout().setVisibility(View.GONE);
            }
        });
    }

    public void expand() {
        setIsCollapsed(false);
        getOptionsLayout().setVisibility(View.VISIBLE);
        for (var v : getOnClickCollapseTriggerViews()) {
            gB.a(v, -180, null);
        }
        gB.b(getOptionsLayout(), 300, null);
    }

    public final boolean shouldAnimateNextTransformation() {
        return animateNextTransformation;
    }

    public final void setAnimateNextTransformation(boolean animateNextTransformation) {
        this.animateNextTransformation = animateNextTransformation;
    }
}