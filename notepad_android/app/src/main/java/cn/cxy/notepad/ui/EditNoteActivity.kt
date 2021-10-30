package cn.cxy.notepad.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.notepad.R
import cn.cxy.notepad.db.Note
import cn.cxy.notepad.db.NoteRepository
import kotlinx.android.synthetic.main.activity_edit_note.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 分享编程技术，没啥深度，但看得懂，适合初学者。
 * Java | 安卓 | 前端 | 小程序 | 鸿蒙
 * 公众号：花生皮编程
 */
class EditNoteActivity : AppCompatActivity() {
    private var noteRepository = NoteRepository()
    private var noteId: Int = 0
    private var note: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        noteId = intent.getIntExtra("noteId", 0)
        initViews()
        initListeners()
        queryData()
    }

    private fun queryData() {
        GlobalScope.launch(Dispatchers.Main) {
            note = noteRepository.query(noteId)
            contentET.setText(note?.content)
        }
    }

    private fun initViews() {
        listNoteToolbar.inflateMenu(R.menu.edit_note_menu)
        listNoteToolbar.setNavigationOnClickListener { saveNoteAndFinish() }
        initMenu()
    }

    private fun initListeners() {
        listNoteToolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.submitNoteMenuItem -> saveNoteAndFinish()
                R.id.delNoteMenuItem -> trashNote()
            }
            false
        }
    }

    private fun trashNote() {
        GlobalScope.launch { noteRepository.del(noteId) }
        finish()
    }

    private fun initMenu() {
        listNoteToolbar.menu.findItem(R.id.delNoteMenuItem).isVisible = true
    }

    private fun saveNoteAndFinish() {
        if (contentET.text.toString().isNotBlank()) {
            saveNote(contentET.text.toString())
        }
        finish()
    }

    private fun saveNote(content: String) {
        note?.let {
            it.content = content
            GlobalScope.launch { noteRepository.update(it) }
        }
    }
}
