package jaime.galiana.ejemlo5infinitescroll.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jaime.galiana.ejemlo5infinitescroll.Modelos.ToDo;
import jaime.galiana.ejemlo5infinitescroll.R;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {

    private List<ToDo> objects; //lista de tareas
    private int resource; //la fila, la vista para construir la fila
    private Context context; //la actividad que tiene el recycler view para mostrar

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //instancia tantos elementos como quepan en pantalla
        View todoView = LayoutInflater.from(context).inflate(resource, null);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        todoView.setLayoutParams(lp);

        return new ToDoVH(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        //se llama de forma automatica en base al elemento que tenemos que rellenar
        ToDo toDo = objects.get(position);

        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdate("¿SEGURO QUE QUIERES CAMBIAR?", toDo).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        //sirve para que el adapter averigue cuantos objetos va a mostrar
        return objects.size();
    }
    private AlertDialog confirmUpdate(String titulo, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    public class ToDoVH extends RecyclerView.ViewHolder {
        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado;

        public ToDoVH(@NonNull View itemView) {
            super(itemView);

            lbTitulo = itemView.findViewById(R.id.lbTituloToDoViewModel);
            lbContenido = itemView.findViewById(R.id.lbContenidoToDoViewModel);
            lbFecha = itemView.findViewById(R.id.lbFechaToDoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);
        }
    }
}
