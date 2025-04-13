import os
import json
import re

def replace_forge_with_c(directory):
    """
    遍历目录及其子目录下的所有JSON文件，将内容中的 "forge" 替换为 "c"，
    同时替换标签引用中的 "#forge:" 为 "#c:"，以及 "tag": "forge: 为 "tag": "c:
    """
    # 遍历所有文件和子目录
    for root, dirs, files in os.walk(directory):
        for filename in files:
            if filename.endswith(".json"):
                filepath = os.path.join(root, filename)
                try:
                    # 读取文件内容
                    with open(filepath, "r", encoding="utf-8") as f:
                        content = f.read()

                    # 替换命名空间
                    modified_content = content.replace('"forge"', '"c"')
                    
                    # 替换标签引用
                    modified_content = modified_content.replace('"#forge:', '"#c:')
                    
                    # 替换tag字段中的forge引用
                    modified_content = modified_content.replace('"tag": "forge:', '"tag": "c:')
                    
                    # 替换fluidTag字段中的forge引用
                    modified_content = modified_content.replace('"fluidTag": "forge:', '"fluidTag": "c:')
                    
                    # 使用正则表达式替换字符串中的标签引用
                    modified_content = re.sub(r'([^"])#forge:', r'\1#c:', modified_content)
                    
                    # 使用正则表达式替换其他可能的forge命名空间引用
                    modified_content = re.sub(r'"([^"]*?)forge:', r'"\1c:', modified_content)

                    # 如果文件内容有变化，才写入
                    if content != modified_content:
                        # 写入修改后的内容
                        with open(filepath, "w", encoding="utf-8") as f:
                            f.write(modified_content)
                        print(f"已修改文件: {filepath}")
                    else:
                        print(f"文件无需修改: {filepath}")

                except Exception as e:
                    print(f"处理文件 {filepath} 时出错: {str(e)}")

if __name__ == "__main__":
    # 设置目标目录（例如当前目录）
    target_directory = input("请输入要处理的文件夹路径（直接回车默认为当前目录）: ").strip()
    target_directory = target_directory if target_directory else os.getcwd()

    # 执行替换
    replace_forge_with_c(target_directory)
    print("处理完成！")