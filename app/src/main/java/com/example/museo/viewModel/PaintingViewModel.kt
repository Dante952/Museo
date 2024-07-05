import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.museo.model.Painting

class PaintingViewModel : ViewModel() {
    // Lista mutable de pinturas (para demostración)
    private val _paintings = mutableStateListOf(
        Painting("Painting 1", "Author 1", "Description 1", "https://example.com/image1.jpg"),
        Painting("Painting 2", "Author 2", "Description 2", "https://example.com/image2.jpg"),
        Painting("Painting 3", "Author 3", "Description 3", "https://example.com/image3.jpg")
    )

    // Método para obtener la lista de pinturas (para demostración)
    fun getPaintings(): List<Painting> {
        return _paintings
    }

    // Método para actualizar el título de una pintura
    fun updateTitle(index: Int, newTitle: String) {
        if (index >= 0 && index < _paintings.size) {
            _paintings[index] = _paintings[index].copy(title = newTitle)
        }
    }
}
