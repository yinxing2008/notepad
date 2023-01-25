package cn.lblbc.note.db

import cn.lblbc.note.db.AppDatabase
import cn.lblbc.note.db.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
class NoteRepository {
    private val noteDao = AppDatabase.getInstance().noteDao()
    suspend fun query(noteId: Int): Note {
        return withContext(Dispatchers.IO) { noteDao.query(noteId) }
    }

    suspend fun query(): List<Note> {
        return withContext(Dispatchers.IO) { noteDao.query() }
    }

    suspend fun add(name: String) {
        withContext(Dispatchers.IO) {
            val note = Note(0, name)
            noteDao.add(note)
        }
    }

    suspend fun update(note: Note) {
        withContext(Dispatchers.IO) { noteDao.update(note) }
    }

    suspend fun del(id: Int) {
        withContext(Dispatchers.IO) {
            val note = Note(id, "")
            noteDao.del(note)
        }
    }
}