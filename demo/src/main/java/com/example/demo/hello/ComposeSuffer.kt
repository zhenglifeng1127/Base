package com.example.demo.hello

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.base.config.AppManager
import com.android.base.utils.openNext
import com.example.demo.R
import com.example.demo.banner.ui.BannerPager
import com.example.demo.dialog.DialogActivity
import com.example.demo.title.LocationBar


@Preview(showBackground = true)
@Composable
fun rootSuffer() {

    /**
     * surface可以定义画布大小代替限定符适配
     */
    Surface(modifier = Modifier.width(375.dp)) {

        BottomView()

    }
}

@Composable
fun Index() {
    Column {
        LocationBar()

    }

}


@Composable
fun AppCenter(modifier: Modifier) {

}


@Composable
fun Hot(modifier: Modifier) {

}

@Composable
fun MySelf(modifier: Modifier) {

}


@Composable
fun BottomView() {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(CourseTabs.HOME_PAGE) }
    val tabs = CourseTabs.values()
    Scaffold(
        backgroundColor = colorResource(id = R.color.bgColor),
        bottomBar = {
            BottomNavigation(modifier = Modifier.height(56.dp)) {
                tabs.forEach { tab ->
                    BottomNavigationItem(selected = tab == selectedTab,
                        onClick = { setSelectedTab(tab) },
                        icon = {
                            Icon(tab.icon, contentDescription = null)
                        },
                        label = {
                            Text(stringResource(tab.title).toUpperCase())
                        })
                }

            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (selectedTab) {
            CourseTabs.HOME_PAGE -> Index()
            CourseTabs.PROJECT -> AppCenter(modifier)
            CourseTabs.OFFICIAL_ACCOUNT -> Hot(modifier)
            CourseTabs.MINE -> MySelf(modifier)
        }

    }

}

enum class CourseTabs(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    HOME_PAGE(R.string.home_page, Icons.Filled.Favorite),
    PROJECT(R.string.project, Icons.Filled.Favorite),
    OFFICIAL_ACCOUNT(R.string.official_account, Icons.Filled.Favorite),
    MINE(R.string.mine, Icons.Filled.Favorite)
}
