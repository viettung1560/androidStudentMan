package vn.edu.hust.studentman

import android.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId
    holder.imageEdit.setOnClickListener {

      val dialogView = LayoutInflater.from(holder.itemView.context)
        .inflate(R.layout.layout_alert_dialog, null)

      val editHoten = dialogView.findViewById<EditText>(R.id.edit_hoten)
      val editMssv = dialogView.findViewById<EditText>(R.id.edit_mssv)
      editHoten.setText(student.studentName);
      editMssv.setText(student.studentId);

      AlertDialog.Builder(holder.itemView.context)
        .setTitle("Cập nhật thông tin sinh viên")
        .setView(dialogView)
        .setPositiveButton("OK") { _, _ ->
          val hoten = editHoten.text.toString()
          val mssv = editMssv.text.toString()

          student.studentName = hoten
          student.studentId = mssv

          this.notifyItemChanged(position)
        }
        .setNegativeButton("Cancel", null)
        .show()
        .setCanceledOnTouchOutside(false)
    }
    holder.imageRemove.setOnClickListener {

      AlertDialog.Builder(holder.itemView.context)
        .setTitle("Xóa thông tin sinh viên")
        .setMessage(Html.fromHtml("<b>${student.studentName}</b><br><i>${student.studentId}</i>", Html.FROM_HTML_MODE_LEGACY))
        .setPositiveButton("Yes"
        ) { _, _ ->
          students.removeAt(position)
          this.notifyItemRemoved(position)

          Snackbar.make(it, "Xóa thành công", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
              students.add(position, student)
              this.notifyItemInserted(position)
            }
            .show()
        }
        .setNegativeButton("No", null)
        .show()
        .setCanceledOnTouchOutside(false)
    }
  }
}