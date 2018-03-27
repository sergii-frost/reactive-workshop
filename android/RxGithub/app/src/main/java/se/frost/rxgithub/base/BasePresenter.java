package se.frost.rxgithub.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * RxGithub
 * Created by Sergii Nezdolii on 2018-03-27.
 * <p>
 * Copyright (c) 2018 Frost°. All rights reserved.
 */
public abstract class BasePresenter<V> {

    protected V view;
    protected final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Called when we are ready to use the view
     *
     * @param view The view to be interacting with
     */
    public void attachView(V view) {
        this.view = view;
    }

    /**
     * Called when the view has been destroyed
     */
    public void detachView() {
        //dispose all disposable
        disposables.clear();
        view = null;
    }

    /**
     * Indicates if the view is available to use
     */
    public boolean isViewAttached() {
        return view != null;
    }

}
