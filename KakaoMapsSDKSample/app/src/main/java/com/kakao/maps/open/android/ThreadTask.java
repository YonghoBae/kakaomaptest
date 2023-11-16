package com.kakao.maps.open.android;

public abstract class ThreadTask<T, V> implements Runnable {
    T argument;

    V result;

    final public void execute(final T arg) {
        argument = arg;

        Thread thread = new Thread(this);
        thread.start();

        try {
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            onPostExecute(null);
            return;
        }

        onPostExecute(result);
    }

    @Override
    public void run() {
        result = doInBackground(argument);
    }

    // doInBackground
    protected abstract V doInBackground(T arg);

    // onPostExecute
    protected abstract void onPostExecute(V result);
}