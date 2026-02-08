package io.mishka.voyager.details.impl.ui.suggest

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import io.mishkav.voyager.core.ui.theme.VoyagerTheme
import io.mishkav.voyager.core.ui.uikit.button.VoyagerButton
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonSizes
import io.mishkav.voyager.core.ui.uikit.button.VoyagerDefaultButtonStyles
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResult
import io.mishkav.voyager.core.ui.uikit.shimmer.placeholderFadeConnecting
import io.mishkav.voyager.features.navigation.api.LocalRootNavigation
import org.jetbrains.compose.resources.stringResource
import voyager.features.details.impl.generated.resources.Res
import voyager.features.details.impl.generated.resources.details_voyager_ai
import voyager.features.details.impl.generated.resources.details_voyager_ai_button
import voyager.features.details.impl.generated.resources.details_voyager_ai_error_subtitle
import voyager.features.details.impl.generated.resources.details_voyager_ai_error_title

@Composable
fun CountryAiSuggestScreen(
    viewModel: CountryAiSuggestViewModel,
    modifier: Modifier = Modifier,
) {
    val aiSuggestsState = viewModel.aiSuggestsState.collectAsStateWithLifecycle()

    CountryAiSuggestScreenContent(
        aiSuggestsState = aiSuggestsState,
        modifier = modifier,
    )
}

@Composable
private fun CountryAiSuggestScreenContent(
    aiSuggestsState: State<UIResult<String>>,
    modifier: Modifier = Modifier,
) {
    val rootNavigation = LocalRootNavigation.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.details_voyager_ai),
            style = VoyagerTheme.typography.h2,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(16.dp))

        AnimatedContent(
            modifier = Modifier.fillMaxWidth(),
            targetState = aiSuggestsState.value
        ) { state ->
            when (state) {
                is UIResult.Error -> {
                    ErrorContent(modifier = Modifier.fillMaxWidth())
                }

                is UIResult.Loading, is UIResult.Nothing -> {
                    LoadingContent()
                }

                is UIResult.Success -> {
                    SuccessContent(
                        text = state.data,
                        dismiss = rootNavigation::closeBottomSheet,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.details_voyager_ai_error_title),
            style = VoyagerTheme.typography.h3,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.details_voyager_ai_error_subtitle),
            style = VoyagerTheme.typography.bodyBold,
            color = VoyagerTheme.colors.white,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(800.dp)
            .placeholderFadeConnecting(
                shape = RoundedCornerShape(24.dp),
            )
    )
}

@Composable
private fun SuccessContent(
    text: String,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Markdown(
            content = text,
            colors = markdownColor(
                text = VoyagerTheme.colors.white,
            ),
            typography = markdownTypography(
                h1 = VoyagerTheme.typography.h1,
                h2 = VoyagerTheme.typography.h2,
                h3 = VoyagerTheme.typography.h3,
                h4 = VoyagerTheme.typography.h3,
                h5 = VoyagerTheme.typography.h3,
                h6 = VoyagerTheme.typography.h3,
                text = VoyagerTheme.typography.body,
                code = VoyagerTheme.typography.body.copy(fontFamily = FontFamily.Monospace),
                quote = VoyagerTheme.typography.body.plus(SpanStyle(fontStyle = FontStyle.Italic)),
                paragraph = VoyagerTheme.typography.bodyBold,
                ordered = VoyagerTheme.typography.bodyBold,
                list = VoyagerTheme.typography.bodyBold,
                textLink = TextLinkStyles(
                    style = VoyagerTheme.typography.bodyBold.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    ).toSpanStyle()
                ),
            )
        )

        Spacer(Modifier.height(12.dp))

        VoyagerButton(
            modifier = Modifier.fillMaxWidth(),
            style = VoyagerDefaultButtonStyles.custom(
                containerColor = VoyagerTheme.colors.white.copy(alpha = 0.2f),
                contentColor = VoyagerTheme.colors.white,
                iconColor = Color.Unspecified,
            ),
            size = VoyagerDefaultButtonSizes.buttonXL(),
            text = stringResource(Res.string.details_voyager_ai_button),
            onClick = dismiss,
        )

        Spacer(Modifier.height(12.dp))
    }
}
