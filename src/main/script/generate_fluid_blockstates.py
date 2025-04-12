import os
import json

# 流体列表，从日志中提取
fluids = [
    "doburoku", "sake", "shouchu", "beer", "whiskey",
    "red_wine", "white_wine", "brandy", "rum", "champagne"
]

# 目标目录
target_dir = "F:/code/mcmod/project/sakura_orihime/src/main/resources/assets/sakura/blockstates"
os.makedirs(target_dir, exist_ok=True)

# 为每个流体创建方块状态JSON文件
for fluid in fluids:
    # 创建方块状态定义
    blockstate = {}

    # 添加变体定义
    variants = {}

    # 为每个液体等级(0-15)创建变体
    for level in range(16):
        # 使用Minecraft原版水模型
        variant_key = f"level={level}"
        variants[variant_key] = {
            "model": "minecraft:block/water"
        }

    blockstate["variants"] = variants

    # 写入JSON文件
    file_path = os.path.join(target_dir, f"{fluid}.json")
    with open(file_path, "w") as f:
        json.dump(blockstate, f, indent=2)

    print(f"已创建 {fluid}.json")

print("所有流体方块状态文件已生成完成！")