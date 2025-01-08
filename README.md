# HeyGen-Video-Translation-Project
HeyGen Video Translation server and client architecture

# Video Translation Project

Welcome to the Video Translation Project! This repository contains two main components:

1. **videotranslation**: The server responsible for simulating video translation jobs.
2. **translation-client**: The client library that interacts with the server and includes integration tests.

To get started, please follow the instructions below:

---

## Setup Instructions

### Step 1: Run the Server

1. Navigate to the `videotranslation` folder.
   ```bash
   cd videotranslation
   ```
2. Follow the instructions in the `ServerSetup_README.md` to set up and start the server.

   This includes:
   - Setting up any dependencies.
   - Running the server to handle video translation jobs.

   Ensure the server is running before proceeding to the next step.

---

### Step 2: Run the Integration Tests

1. Navigate to the `translation-client` folder.
   ```bash
   cd translation-client
   ```
2. Follow the instructions in the `ClientLibrary_README.md` to run the integration tests.

   These tests will:
   - Use the client library to interact with the server.
   - Validate the functionality of the server by testing various scenarios.

---

## Notes

- Ensure that the server is running and accessible before running the client tests.
- For detailed documentation and usage, refer to the respective READMEs in each folder:
  - **Server**: `videotranslation/ServerSetup_README.md`
  - **Client**: `translation-client/ClientLibrary_README.md`

---

Thank you for exploring the Video Translation Project! If you encounter any issues, feel free to raise them in the issue tracker.
