package org.example.imitate.tomcat;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public abstract class LifecycleBase implements Lifecycle {
    private final CopyOnWriteArrayList<LifecycleListener> listeners = new CopyOnWriteArrayList<>();
    private final AtomicReference<LifecycleState> state = new AtomicReference<>(LifecycleState.NEW);

    @Override
    public void start() throws LifecycleException {
        if (!state.compareAndSet(LifecycleState.NEW, LifecycleState.STARTING)) {
            return;
        }
        try {
            startInternal();
            state.set(LifecycleState.STARTED);
            fireLifecycleEvent(LifecycleEvent.START, null);
        } catch (Exception e) {
            state.set(LifecycleState.FAILED);
            throw new LifecycleException("启动失败", e);
        }
    }

    @Override
    public void stop() throws LifecycleException {
        if (!state.compareAndSet(LifecycleState.STARTED, LifecycleState.STOPPING)) {
            return;
        }
        try {
            stopInternal();
            state.set(LifecycleState.STOPPED);
            fireLifecycleEvent(LifecycleEvent.STOP, null);
        } catch (Exception e) {
            state.set(LifecycleState.FAILED);
            throw new LifecycleException("停止失败", e);
        }
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        listeners.remove(listener);
    }

    @Override
    public LifecycleState getState() {
        return state.get();
    }

    protected abstract void startInternal() throws LifecycleException;
    protected abstract void stopInternal() throws LifecycleException;

    protected void fireLifecycleEvent(LifecycleEvent event, Object data) {
        for (LifecycleListener listener : listeners) {
            listener.lifecycleEvent(event);
        }
    }
} 