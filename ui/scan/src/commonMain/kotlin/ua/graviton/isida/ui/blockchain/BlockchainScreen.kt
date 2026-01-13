package ua.crypto.ui.blockchain

import androidx.compose.animation.*
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.ViewInAr
import androidx.compose.material.icons.rounded.Waves
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.crypto.ui.common.theme.AppTheme
import ua.crypto.ui.di.injectedViewModel
import ua.graviton.isida.ui.navigation.HomeTabScreen
import ua.crypto.ui.resources.Res
import ua.crypto.ui.resources.rail_screen_blockchain

@Serializable
data object BlockchainScreen : HomeTabScreen {
    override val icon: ImageVector = Icons.AutoMirrored.Rounded.ReceiptLong
    override val title: StringResource = Res.string.rail_screen_blockchain
}

@Composable
internal fun BlockchainScreen(
    viewModel: BlockchainViewModel = injectedViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BlockchainScreen(
        state = state,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlockchainScreen(
    state: BlockchainViewState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(BlockchainScreen.title)) },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        BlockchainList(
            feed = state.feed,
            modifier = Modifier.padding(paddings)
        )
    }
}

@Composable
private fun BlockchainList(
    feed: List<BlockchainFeedItem>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // 1. Logic to show/hide the "Scroll to Top" button
    // We show it if the user is more than 3 items deep
    val showScrollToTop by remember { derivedStateOf { listState.firstVisibleItemIndex > 3 } }

    val isUserDragging by listState.interactionSource.collectIsDraggedAsState()
    LaunchedEffect(feed) {
        if (!isUserDragging && listState.firstVisibleItemIndex < 3) {
            listState.scrollToItem(0)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = feed,
                key = { it.id } // Critical for performance with moving lists
            ) { item ->
                when (item) {
                    is BlockchainFeedItem.Block -> BlockItem(item)
                    is BlockchainFeedItem.Transaction -> TransactionItem(item)
                }
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(listState),
            modifier = Modifier.fillMaxHeight()
                .align(Alignment.CenterEnd),
        )

        // 4. Floating Action Button (Scroll to Top)
        AnimatedVisibility(
            visible = showScrollToTop,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            SmallFloatingActionButton(
                onClick = {
                    scope.launch {
                        // This snaps back to 0, which effectively re-enables
                        // the "Stickiness" logic in the LaunchedEffect above.
                        listState.scrollToItem(0)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = "Scroll to Top"
                )
            }
        }
    }
}

// --- ITEM: TRANSACTION ---

@Composable
private fun TransactionItem(
    item: BlockchainFeedItem.Transaction,
    modifier: Modifier = Modifier
) {
    val isWhale = item.isHighValue
    val mainColor = if (isWhale) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

    // Single Row Container
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp) // Fixed height for "List Item" feel
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. ICON (Minimalist, no background circle)
        Icon(
            imageVector = if (isWhale) Icons.Rounded.Waves else Icons.Rounded.AttachMoney,
            contentDescription = null,
            tint = mainColor,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // 2. AMOUNT
        Text(
            text = item.totalValueDisplay,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = mainColor
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 3. ADDRESS FLOW (Takes remaining space)
        // We use a nested Row with weights so chips shrink nicely
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // Center addresses in the gap
        ) {
            // Sender
            CompactAddressChip(
                text = item.senderAddress,
                modifier = Modifier.weight(1f, fill = false) // Shrink if needed
            )

            // Arrow (Very tiny)
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 2.dp).size(10.dp),
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )

            // Receiver
            CompactAddressChip(
                text = item.receiverAddress,
                modifier = Modifier.weight(1f, fill = false)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 4. TIME (and tiny fee indicator dot)
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Fee Dot (Color coded)
            val feeColor = when (item.feeLevel) {
                FeeLevel.HIGH -> MaterialTheme.colorScheme.error
                FeeLevel.MEDIUM -> MaterialTheme.colorScheme.tertiary
                else -> MaterialTheme.colorScheme.primary
            }
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(feeColor, CircleShape)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = item.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }

    // Ultra-thin divider (full width or indented)
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
        thickness = 1.dp
    )
}

// --- ITEM: BLOCK ---

@Composable
private fun BlockItem(
    item: BlockchainFeedItem.Block,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.ViewInAr, // Cube icon
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "New Block Found: #${item.height}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.1f)
            )

            // Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BlockStat(label = "TXs", value = item.txCount.toString())
                BlockStat(label = "Sent", value = item.totalSentDisplay)
                BlockStat(label = "Reward", value = item.minerRewardDisplay)
            }
        }
    }
}

// --- SUB-COMPONENTS ---

@Composable
private fun CompactAddressChip(text: String, modifier: Modifier = Modifier) {
    // No background surface to save visual clutter in single row
    // Just text with monospace font
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                RoundedCornerShape(2.dp)
            )
            .padding(horizontal = 2.dp, vertical = 0.dp) // minimal padding
    )
}

@Composable
private fun BlockStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        BlockchainScreen(
            state = BlockchainViewState.Init,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItems() {
    AppTheme {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TransactionItem(
                BlockchainFeedItem.Transaction(
                    id = "1", timestamp = "14:20:01",
                    totalValueDisplay = "0.0042 BTC",
                    senderAddress = "1A8z...f4j2", receiverAddress = "bc1q...9s7d",
                    isHighValue = false, feeLevel = FeeLevel.LOW, inputCount = 1, outputCount = 2
                )
            )
            TransactionItem(
                BlockchainFeedItem.Transaction(
                    id = "2", timestamp = "14:20:05",
                    totalValueDisplay = "152.40 BTC",
                    senderAddress = "Multiple Inputs", receiverAddress = "bc1q...5x99",
                    isHighValue = true, feeLevel = FeeLevel.HIGH, inputCount = 5, outputCount = 2
                )
            )
            BlockItem(
                BlockchainFeedItem.Block(
                    id = "3", timestamp = "14:25:00", height = 824501,
                    txCount = 3502, totalSentDisplay = "4500 BTC", minerRewardDisplay = "6.25 BTC"
                )
            )
        }
    }
}