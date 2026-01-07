package sk.sksv.newsappcompose.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import sk.sksv.newsappcompose.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConvertor {


    @TypeConverter
    fun sourceToString(source: Source): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source: String): Source {
        return source.split(",").let {
            Source(id = it[0], name = it[1])
        }
    }
}