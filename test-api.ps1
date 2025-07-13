# Simple API Test Script for Praesidium

# --- Configuration ---
# Read .env file for SERVER_PORT
try {
    if (Test-Path .env) {
        $envVars = Get-Content .env | ConvertFrom-StringData
        $port = $envVars.SERVER_PORT
    }
} catch {
    # Ignore parsing errors
}

# Set default port if not found or invalid
if (-not $port -or $port -eq "") {
    $port = 8080
}

$baseUrl = "http://localhost:$port/api"
Write-Host "Targeting API at: $baseUrl"

# --- Test Data ---
# These UUIDs must match the ones in V7__Seed_real_review_data.sql
$pendingReviewIdForApproval = "a1b2c3d4-0001-4000-8000-000000000001"
$approvedReviewIdForGet = "a1b2c3d4-0002-4000-8000-000000000002"
$itemIdsForBatchReject = @(
    "a1b2c3d4-0004-4000-8000-000000000004", # An approved item
    "a1b2c3d4-0005-4000-8000-000000000005"  # A pending item
)

# --- Helper function for pretty printing JSON ---
function Print-Json($json) {
    if ($json -is [pscustomobject] -or $json -is [array]) {
        $json | ConvertTo-Json -Depth 5
    } else {
        $json # Assume it's already a string
    }
}


# --- Test Execution ---
Write-Host "`n" + ("-"*50)
Write-Host "--- Starting Praesidium API Tests ---"
Write-Host ("-"*50)

try {
    # 1. Get Review Queue Stats
    Write-Host "`n[1/8] Getting review queue stats..."
    $stats = Invoke-RestMethod -Uri "$baseUrl/review-queue/stats" -Method Get
    Print-Json $stats

    # 2. Get all PENDING review items
    Write-Host "`n[2/8] Getting all PENDING review items..."
    $pendingItems = Invoke-RestMethod -Uri "$baseUrl/review-queue/items?status=PENDING" -Method Get
    Print-Json $pendingItems

    # 3. Get a specific APPROVED review item by ID
    Write-Host "`n[3/8] Getting specific APPROVED item details by ID: $approvedReviewIdForGet"
    $approvedItem = Invoke-RestMethod -Uri "$baseUrl/review-queue/items/$approvedReviewIdForGet" -Method Get
    Print-Json $approvedItem

    # 4. Approve a PENDING review item
    Write-Host "`n[4/8] Approving PENDING item with ID: $pendingReviewIdForApproval"
    $decisionBody = @{
        itemId = $pendingReviewIdForApproval
        decision = "approve"
        standardQuestion = "This is a new standard question from test script"
        suggestedAnswer = "This is the approved answer."
        comment = "Looks good. Approved by test script."
    } | ConvertTo-Json
    $approvedDecision = Invoke-RestMethod -Uri "$baseUrl/review-queue/decision" -Method Post -Body $decisionBody -ContentType "application/json"
    Print-Json $approvedDecision

    # 5. Verify the item is now APPROVED
    Write-Host "`n[5/8] Verifying item $pendingReviewIdForApproval is now APPROVED..."
    $verifiedItem = Invoke-RestMethod -Uri "$baseUrl/review-queue/items/$pendingReviewIdForApproval" -Method Get
    Print-Json $verifiedItem
    if ($verifiedItem.data.status -eq "APPROVED") {
        Write-Host "Verification successful: Status is APPROVED." -ForegroundColor Green
    } else {
        Write-Host "Verification FAILED: Status is not APPROVED." -ForegroundColor Red
    }
    
    # 6. Batch reject items
    Write-Host "`n[6/8] Batch rejecting items..."
    $batchBody = @{
        itemIds = $itemIdsForBatchReject
        operation = "reject"
        comment = "Batch rejected by test script"
    } | ConvertTo-Json
    $batchResult = Invoke-RestMethod -Uri "$baseUrl/review-queue/batch-operation" -Method Post -Body $batchBody -ContentType "application/json"
    Print-Json $batchResult

    # 7. Get available sources
    Write-Host "`n[7/8] Getting available sources..."
    $sources = Invoke-RestMethod -Uri "$baseUrl/review-queue/sources" -Method Get
    Print-Json $sources

    # 8. Get available tags
    Write-Host "`n[8/8] Getting available tags..."
    $tags = Invoke-RestMethod -Uri "$baseUrl/review-queue/tags" -Method Get
    Print-Json $tags

}
catch {
    Write-Host "`nAn error occurred during API testing:" -ForegroundColor Red
    $statusCode = $_.Exception.Response.StatusCode.value__
    $responseContent = $_.Exception.Response.Content
    Write-Host "Status Code: $statusCode" -ForegroundColor Red
    Write-Host "Response:" -ForegroundColor Red
    # Try to parse and pretty-print if it's JSON
    try {
        $responseObject = $responseContent | ConvertFrom-Json
        Print-Json $responseObject
    } catch {
        Write-Host $responseContent
    }
    exit 1
}

Write-Host "`n" + ("-"*50)
Write-Host "--- API Tests Completed Successfully ---" -ForegroundColor Green
Write-Host ("-"*50) 