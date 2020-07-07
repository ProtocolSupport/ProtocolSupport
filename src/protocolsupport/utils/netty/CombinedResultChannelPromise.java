package protocolsupport.utils.netty;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class CombinedResultChannelPromise implements ChannelPromise {

    protected final ChannelPromise promise;
	protected final Channel channel;

	public CombinedResultChannelPromise(Channel channel, ChannelPromise promise) {
		this.channel = channel;
		this.promise = promise;
	}

	@Override
	public Channel channel() {
		return channel;
	}

	protected final AtomicBoolean canRegister = new AtomicBoolean(true);
	protected final AtomicInteger registers = new AtomicInteger(1);
	protected final AtomicReference<Throwable> fail = new AtomicReference<>();

	public void registerUsage() {
		if (canRegister.get()) {
			registers.getAndIncrement();
			return;
		}
		throw new IllegalStateException("Registration is closed");
	}

	public void closeUsageRegister() {
		if (canRegister.compareAndSet(true, false)) {
			addResult(null);
			return;
		}
		throw new IllegalStateException("Registration already closed");
	}

	protected boolean addResult(Throwable exception) {
		int currentRegisters = registers.decrementAndGet();
		if (currentRegisters < 0) {
			return false;
		}
		if (exception != null && !fail.compareAndSet(null, exception)) {
			fail.get().addSuppressed(exception);
		}
		if (currentRegisters == 0 && !canRegister.get()) {
			Throwable resultException = fail.get();
			if (resultException == null) {
				promise.trySuccess();
			} else {
				promise.tryFailure(resultException);
			}
		}
		return true;
	}

	@Override
	public boolean trySuccess(Void result) {
		return addResult(null);
	}

	@Override
	public boolean trySuccess() {
		return addResult(null);
	}

	@Override
	public boolean tryFailure(Throwable exception) {
		return addResult(exception);
	}

	@Override
	public ChannelPromise setSuccess() {
		addResult(null);
		return this;
	}

	@Override
	public ChannelPromise setSuccess(Void result) {
		addResult(null);
		return this;
	}

	@Override
	public ChannelPromise setFailure(Throwable exception) {
		addResult(exception);
		return this;
	}

	@Override
	public boolean isDone() {
		return promise.isDone();
	}

	@Override
	public boolean isSuccess() {
		return promise.isSuccess();
	}

	@Override
	public Throwable cause() {
		return promise.cause();
	}

	@Override
	public ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> addListener) {
		promise.addListener(addListener);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... addListeners) {
		promise.addListeners(addListeners);
		return this;
	}

	@Override
	public ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> removeListener) {
		promise.removeListener(removeListener);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... removeListeners) {
		promise.removeListeners(removeListeners);
		return this;
	}

	@Override
	public ChannelPromise await() throws InterruptedException {
		promise.await();
		return this;
	}

	@Override
	public ChannelPromise awaitUninterruptibly() {
		promise.awaitUninterruptibly();
		return this;
	}

	@Override
	public boolean await(long time, TimeUnit unit) throws InterruptedException {
		return promise.await(time, unit);
	}

	@Override
	public boolean await(long time) throws InterruptedException {
		return promise.await(time);
	}

	@Override
	public boolean awaitUninterruptibly(long time, TimeUnit unit) {
		return promise.awaitUninterruptibly(time, unit);
	}

	@Override
	public boolean awaitUninterruptibly(long time) {
		return promise.awaitUninterruptibly(time);
	}

	@Override
	public ChannelPromise sync() throws InterruptedException {
		promise.sync();
		return this;
	}

	@Override
	public ChannelPromise syncUninterruptibly() {
		promise.syncUninterruptibly();
		return this;
	}

	@Override
	public Void getNow() {
		return promise.getNow();
	}

	@Override
	public Void get() throws InterruptedException, ExecutionException {
		return promise.get();
	}

	@Override
	public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return promise.get(timeout, unit);
	}

	@Override
	public boolean isVoid() {
		return false;
	}

	@Override
	public ChannelPromise unvoid() {
		return this;
	}

	@Override
	public boolean isCancellable() {
		return false;
	}

	@Override
	public boolean setUncancellable() {
		return true;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean cancel(boolean interrupt) {
		return false;
	}

}
