#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ${ClassName} extends RecyclerView.Adapter<${ClassName}.ViewHolder> {
    private final String TAG = ${ClassName}.class.getSimpleName();
    
    private Context context;
    private ArrayList<${Model}> mListData;
    
    private IListener listener;

    public void setOnClickListener(IListener listener){
        this.listener = listener;
    }

    public interface IListener{
        void onClick(${Model} data, int position);
    }

    public ${ClassName}(@NonNull Context context,@NonNull ArrayList<${Model}> mListData) {
        this.mListData = mListData;
        this.context = context;
    }

    public ArrayList<${Model}> getDataSource() {
        return mListData;
    }

    public void setDataSource(ArrayList<${Model}> mListData) {
        this.mListData = mListData;
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.mListData.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(${BindingView}Binding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ${BindingView}Binding mView;

        private ViewHolder(${BindingView}Binding mView) {
            super(mView.getRoot());
            this.mView = mView;
        }
    }
}
