package com.robertconstantindinescu.presentation.detection_screen

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.robertconstantindinescu.core.SingleUiEvent
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.model.model_component.BodyLocation
import com.robertconstantindinescu.domain.model.model_component.MoleType
import com.robertconstantindinescu.domain.use_case.ClassifierUseCase
import com.robertconstantindinescu.domain.util.ManageFiles
import com.robertconstantindinescu.presentation.databinding.ActivityDetectorBinding
import com.robertconstantindinescu.presentation.util.BodyParts.Companion.bodyParts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
@AndroidEntryPoint
class DetectorActivity : AppCompatActivity() {
    private lateinit var mClassifier: ClassifierUseCase
    private lateinit var mBitmap: Bitmap
    private lateinit var mBinding: ActivityDetectorBinding

    var selectedItemIndex = 0
    var bodyLocation: String? = null
    private var photoFile: File? = null
    private val viewModel: DetectorViewModel by viewModels()
    private var date: StateFlow<String>? = null

    private var images: File? = null


    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    private val mInputSize = 224
    private val mModelPath = "model.tflite"
    private val mLabelPath = "labels.txt"
    private val mSamplePath = "skin-icon.jpg"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetectorBinding.inflate(layoutInflater)
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(mBinding.root)


        lifecycleScope.launchWhenStarted {
            viewModel.singleUiEvent.collectLatest { singleUiEvent ->
                when(singleUiEvent){
                    is SingleUiEvent.NavigationUp -> {
                        finish()
                    }
                    else -> Unit

                }
            }
        }
        /**
         * AssetsManager --->
         * Provides access to an application's raw asset files; see Resources for the way most
         * applications will want to retrieve their resource data. This class presents a lower-level
         * API that allows you to open and read raw files that have been bundled with the application
         * as a simple stream of bytes.
         */
        mClassifier = ClassifierUseCase(assets, mModelPath, mLabelPath, mInputSize)

        /**
         * resources es una clase de AppCompatActivity que te permite aceder a lso recursos de la app
         * Concreatametne vamos a aceder a todos los recuros que se han guardado en la carpeta assets.
         * Ahora assets reslemtne es una clase AssetManager que te permite acceder a los recuross que
         * se hayan metido como simples buytes. de esa carpeta assets
         * En esta carpeta es donde se guarda los archivos de la pp como fotos etc...
         * Ahora con open lo que hacemos es dar acceso al dato que tenemos en asset, en este caso
         * una imagen. Con use podremos ejecutar un bloque de codigo sore esa iamgen.
         *
         *
         */
        resources.assets.open(mSamplePath).use {
            //con esto vamos a decodificar ese inputStream(que es una succesion de bit) de esa imagen
            //y formamos un bitmap con la imagen.
            mBitmap = BitmapFactory.decodeStream(it)
            //ahora lo que hacemos con ese mBitmap que obtenemos, es escalarlo a nuestro gusto. Transformamos la imagen
            //El meotodo createScaledBitmap() acepta los siguientes parametros.
            //la fente bitmap que es el mBitmap
            //la nueva altura y anchura en pixeles que sera 224
            //filtros par amayor calidad que en este caso lo denamos a falso porque bajaria el rendimietno.
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            //simplemente vamos a asignar a la iamgeview ese bitmap transofmrado.
            mBinding.mPhotoImageView.setImageBitmap(mBitmap)
        }

        mBinding.imgViewCamara.setOnClickListener {

//            photoFile = ManageFiles().createImageFile(this)
//            val fileProvider =
//                FileProvider.getUriForFile( // En base al provider creado en el Manifest.
//                    this,
//                    "com.robertconstantindinescu.klovercleanarchitecture",
//                    photoFile!!
//                )
//
//
//            //llamamos a la camara con un codigo de respuesta en caso de qeu se haya accedido.
//            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//                putExtra(
//                    MediaStore.EXTRA_OUTPUT, fileProvider
//                )
//            }
//            //resultTakePicture.launch(intent)
//            //val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            //si se vuevle a la atividad princiapl desde el intentn camara, lo que hace este metodo es devolver
//            //ese request code en elonActivityResult. cuando la actividad exista, es decir volvemos a ella
//            startActivityForResult(
//                callCameraIntent,
//                mCameraRequestCode
//            ) //cuadno haces la staarr activity for resutl pasas ese codigo que te sera devievleto en el onActityresutl

            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }

        mBinding.imgViewGallery.setOnClickListener {

//            photoFile = ManageFiles().createImageFile(this)
//            val fileProvider =
//                FileProvider.getUriForFile( // En base al provider creado en el Manifest.
//                    this,
//                    "com.robertconstantindinescu.klovercleanarchitecture",
//                    photoFile!!
//                )
//
//
//            //llamamos a la camara con un codigo de respuesta en caso de qeu se haya accedido.
//
//
//            val callGalleryIntent = Intent(Intent.ACTION_PICK).apply {
//                putExtra(
//                    MediaStore.EXTRA_OUTPUT, fileProvider
//                )
//            }
//            callGalleryIntent.type = "image/*"
//            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)

        }
        mBinding.mDetectButton.setOnClickListener {

            /**Choose one body location from custom alert*/

            showBodyPartsDialog(it)
            /*Cuadno pulsamos el boton de detectar llamamos a la calse mClassifier. que ya la habíamos
            * inicializado. y llamamso al metodo recognizeImage. */
            val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
            mBinding.mResultTextView.text = results?.title + "\n Confidence:" + results?.confidence
        }

        mBinding.btnSaveResult.setOnClickListener {

//            val bitmap = mBitmap
//            val bytesArrayOutputStream = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytesArrayOutputStream)
//            val data: ByteArray = bytesArrayOutputStream.toByteArray()



            viewModel.generateDate()
            val melanomaRecord = MelanomaRecord(
                img = mBitmap,
                moleType = if (mBinding.mResultTextView.text.contains("Malignant")) MoleType.fromString(
                    "malignant"
                ) else MoleType.fromString("benign"),
                bodyLocation = BodyLocation.fromString(bodyLocation!!),
                date = viewModel.dateState.value,
                preTestResult = mBinding.mResultTextView.text.toString()
            )


            viewModel.insertMelanomaRecord(melanomaRecord)
        }


    }

    private fun showBodyPartsDialog(view: View) {

        var mutableSelectedItem = bodyParts[selectedItemIndex]

        MaterialAlertDialogBuilder(this)
            .setTitle("Select body part")
            .setSingleChoiceItems(bodyParts, selectedItemIndex) { _, which ->
                selectedItemIndex = which
                mutableSelectedItem = bodyParts[which]

            }
            .setPositiveButton("Ok") { dialog, which ->
                bodyLocation = mutableSelectedItem

            }
            .setNeutralButton("Cancel") { dialog, which ->

            }
            .show()

    }

    /**
     * Con esto vamos a obtener la imagen del intent galeria (requestcode 2) o camara (requestcode 0)
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //si el request code
        if (requestCode == mCameraRequestCode) {
            //si el result code es OK es decir el ususario ha pulsado ok despues de tomar la foto
            //y además los datos que se reciben del intent camera no son nulos

            if (resultCode == Activity.RESULT_OK && data != null) {
//                images = photoFile!!
//                photoFile = null
                /*Entonces a nuestro Bitmap vamos a asociarle esa foto que viene de la camara
                * o mejor dicho ese bitmap porque lo casteamos. Y ademas cuando obtenemos ese bitmap
                * le vamos a asociar una clave, en este caso data. */
                mBitmap = data.extras!!.get("data") as Bitmap
                //escalamos la imagen en de acuerdo a una fucnion que hemos definido abajo.
                mBitmap = scaleImage(mBitmap)
                //indicamos un toast con las dimensiones de la imagen
                val toast = Toast.makeText(
                    this,
                    ("Image crop to: w= ${mBitmap.width} h= ${mBitmap.height}"),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()
                //ahora vamos a coger y setear la iamgen en el imageview.
                mBinding.mPhotoImageView.setImageBitmap(mBitmap)
                mBinding.mResultTextView.text = "Your photo image set now."
            } else {
                //si resulta que el codigo no es ok cuando volvemos de la camara pues nada, un mensaje de que se canceló la camara.
                Toast.makeText(this, "Camera cancel..", Toast.LENGTH_LONG).show()
            }
            /*Si el reqeustcode es el de la camara*/
        } else if (requestCode == mGalleryRequestCode) {
            //si los datos no son nulos
            if (data != null) {
//                images = photoFile!!
//                photoFile = null
                //Ahora vamos a alamcenarnos la dirección de la imagen de la galeria. Es decir la uri
                //con el data hacemos como un get y obtenemos esa uri de la iamgen de al galeria.
                //devuelve //The URI of the data this intent is targeting

                val uri = data.data

                //ahora con esa uri te creas el bitmap.
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                println("Success!!!")
                //lo escalamos
                mBitmap = scaleImage(mBitmap)
                //y ahora vamos a meterlo en el iamgeview.
                mBinding.mPhotoImageView.setImageBitmap(mBitmap)

            }
        } else {
            Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_LONG).show()

        }
    }


    /**
     * Esta funcion v a arecibir un bitmap, que peude no recibir nada por eso indicamos nulo y
     * devovlera un bitmap escalado por nsootrs.
     */
    fun scaleImage(bitmap: Bitmap?): Bitmap {
        //obtenemos la anchura del bitmap original qeu nos llega a de la cámara.
        val orignalWidth = bitmap!!.width
        //obtenemos tambien la altura.
        val originalHeight = bitmap!!.height
        //reeescalamos la imagen  dividientdo 224.0 entre el woth original ---> podemos meter un puto de rutpura pra ver que size tiene.
        val scaleWidth = mInputSize.toFloat() / orignalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        //Nos creamos una matriz.
        val matrix = Matrix()
        //escalamos la matriz de acuerdo a la altura y anchira en pixeles.
        matrix.postScale(scaleWidth, scaleHeight)
        //devolvemos un bitmap tranformado y escalado según la estrucutra de la matriz que hemos defido
        //le pasamos el botmap original de la camapara, le decimos que el primer pixel de la posicion x
        /*es el cero al igual que el pixel del eje y. Leugo definimos la altura y anchura en pixeles
        * que sera 224 ¡, la matriz y el filtro. */
        return Bitmap.createBitmap(bitmap, 0, 0, orignalWidth, originalHeight, matrix, true)
    }
}