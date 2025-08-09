import os
import json

def update_fluid_amounts(data):
    """
    Recursively searches for 'fluid' and 'result_fluid' objects and updates their 'amount'.
    """
    if isinstance(data, dict):
        # Process the "fluid" field
        if "fluid" in data and isinstance(data["fluid"], dict) and "amount" in data["fluid"]:
            original_amount = data["fluid"]["amount"]
            if isinstance(original_amount, (int, float)):
                data["fluid"]["amount"] = int(original_amount * 81)
                print(f"Updated 'fluid.amount' from {original_amount} to {data['fluid']['amount']}")

        # Process the "result_fluid" field
        if "result_fluid" in data and isinstance(data["result_fluid"], dict) and "amount" in data["result_fluid"]:
            original_amount = data["result_fluid"]["amount"]
            if isinstance(original_amount, (int, float)):
                data["result_fluid"]["amount"] = int(original_amount * 81)
                print(f"Updated 'result_fluid.amount' from {original_amount} to {data['result_fluid']['amount']}")

        # Recurse into nested dictionaries and lists
        for key, value in data.items():
            update_fluid_amounts(value)

    elif isinstance(data, list):
        for item in data:
            update_fluid_amounts(item)

    return data

def update_json_file(file_path):
    """
    Reads a JSON file, updates fluid amounts, and writes it back.
    """
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            data = json.load(f)
    except (FileNotFoundError, json.JSONDecodeError) as e:
        print(f"Error reading {file_path}: {e}")
        return False, None

    updated_data = update_fluid_amounts(data)

    # Write the updated data back to the file
    try:
        with open(file_path, 'w', encoding='utf-8') as f:
            json.dump(updated_data, f, indent=2)
        return True, None
    except IOError as e:
        print(f"Error writing to {file_path}: {e}")
        return False, e

def traverse_and_update(directory):
    """
    Traverses a directory and updates all .json files.
    """
    processed_count = 0
    for root, _, files in os.walk(directory):
        for file in files:
            if file.endswith('.json'):
                file_path = os.path.join(root, file)
                print(f"\nProcessing file: {file_path}")
                updated, error = update_json_file(file_path)
                if updated:
                    processed_count += 1

    print(f"\nProcessing complete. {processed_count} files were successfully processed.")

if __name__ == "__main__":
    # Specify the directory containing your JSON files
    # Replace 'path/to/your/directory' with the actual path
    target_directory = r"F:\code\mcmod\project\sakura_orihime\src\main\resources\data\sakura\recipes"

    if os.path.isdir(target_directory):
        print(f"Starting to process JSON files in: {target_directory}")
        traverse_and_update(target_directory)
    else:
        print(f"Error: The directory '{target_directory}' does not exist.")