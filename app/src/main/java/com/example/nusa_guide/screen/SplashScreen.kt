package com.example.nusa_guide.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nusa_guide.R
import com.example.nusa_guide.api.RetrofitInstance
import com.example.nusa_guide.data.DataStoreManager
import com.example.nusa_guide.navigation.NavigationTourScreen
import com.example.nusa_guide.repository.AuthRepository
import com.example.nusa_guide.ui.theme.brandPrimary500
import com.example.nusa_guide.ui.theme.gray50
import com.example.nusa_guide.viewModel.AuthViewModel
import com.example.nusa_guide.viewModel.AuthViewModelFactory
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(
            repository = AuthRepository(
                apiService = RetrofitInstance.api,
                dataStoreManager = DataStoreManager.getInstance(context = LocalContext.current)
            )
        )
    )
) {
    LaunchedEffect(key1 = true) {
        delay(3000L)

        val token = authViewModel.getBearerToken()
        if (!token.isNullOrBlank()) {
            navController.navigate(NavigationTourScreen.HalamanBottom.name)
        } else {
            navController.navigate(NavigationTourScreen.OnBoardingScreen.name)
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = brandPrimary500
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_splash),
                contentDescription = "img-splash-Screen",
                modifier = Modifier
                    .width(251.dp)
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "From",
                    fontSize = 14.sp,
                    color = gray50,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "I Gusti Ngurah Rai",
                    fontSize = 16.sp,
                    color = gray50,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}