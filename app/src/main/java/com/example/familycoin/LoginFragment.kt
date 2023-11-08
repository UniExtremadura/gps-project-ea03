import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.familycoin.R

class LoginFragment : Fragment() {

    private lateinit var loginButton : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         loginButton = view.findViewById<Button>(R.id.loginButton)

        // Manejar el clic del botón de inicio de sesión
        loginButton.setOnClickListener {


            // Aquí puedes agregar la lógica para verificar el inicio de sesión
            // por ejemplo, autenticar al usuario con un servicio de autenticación.
        }
    }
}
