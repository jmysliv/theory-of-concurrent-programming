public class Future<T> {

    private boolean available = false;
    private T data;

    public boolean isAvailable() {
        return available;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
        this.available = true;
    }
}