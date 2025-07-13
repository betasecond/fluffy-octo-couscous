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

$baseUrl = "http://localhost:$port/api/v1"
Write-Host "Targeting API at: $baseUrl"

# --- Test Data ---
# These UUIDs must match the ones in V3__Insert_test_data.sql
$pendingReviewId = "f47ac10b-58cc-4372-a567-0e02b2c3d479"
$approvedReviewId = "f47ac10b-58cc-4372-a567-0e02b2c3d480"

# --- Helper function for pretty printing JSON ---
function Print-Json($json) {
    # Simple check if it's a PSObject that needs conversion
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
    # 1. Get all PENDING review items
    Write-Host "`n[1/4] Getting all PENDING review items..."
    $pendingItems = Invoke-RestMethod -Uri "$baseUrl/reviews?status=PENDING" -Method Get
    Print-Json $pendingItems

    # 2. Get a specific APPROVED review item by ID
    Write-Host "`n[2/4] Getting specific APPROVED item by ID: $approvedReviewId"
    $approvedItem = Invoke-RestMethod -Uri "$baseUrl/reviews/$approvedReviewId" -Method Get
    Print-Json $approvedItem

    # 3. Approve a PENDING review item
    Write-Host "`n[3/4] Approving PENDING item with ID: $pendingReviewId"
    $body = @{
        status = "APPROVED"
        comments = "Looks good. Approved by test script."
    } | ConvertTo-Json
    $approvedDecision = Invoke-RestMethod -Uri "$baseUrl/reviews/$pendingReviewId/decision" -Method Post -Body $body -ContentType "application/json"
    Print-Json $approvedDecision
    Write-Host "Item approved successfully."

    # 4. Verify the item is now APPROVED
    Write-Host "`n[4/4] Verifying item $pendingReviewId is now APPROVED..."
    $verifiedItem = Invoke-RestMethod -Uri "$baseUrl/reviews/$pendingReviewId" -Method Get
    Print-Json $verifiedItem
    if ($verifiedItem.data.status -eq "APPROVED") {
        Write-Host "Verification successful: Status is APPROVED." -ForegroundColor Green
    } else {
        Write-Host "Verification FAILED: Status is not APPROVED." -ForegroundColor Red
    }
}
catch {
    Write-Host "`nAn error occurred during API testing:" -ForegroundColor Red
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
    Write-Host "Response: $($_.Exception.Response.Content)" -ForegroundColor Red
    exit 1
}

Write-Host "`n" + ("-"*50)
Write-Host "--- API Tests Completed Successfully ---" -ForegroundColor Green
Write-Host ("-"*50) 