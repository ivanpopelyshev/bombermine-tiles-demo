def defaultTile(tile) 
	$tileConf.defaultTile(tile)
end

def newGroup(name, attr)
    $tileConf.newGroup(name, attr)
end

def newTile(name, attr)
    $tileConf.newTile(name, attr)
end

def newSlot(name, attr)
    $tileConf.newSlot(name, attr)
end
    
def newItem(name, attr)
    $tileConf.newItem(name, attr)
end

def newChange(name, attr)
    $tileConf.newChange(name, attr)
end

def sub(name, attr = {})
    $tileConf.newTile(name, attr)
end

def getTile(name)
    $tileConf.getTile(name)
end

def getSlot(name)
    $tileConf.getSlot(name)
end
    
def getItem(name)
    $tileConf.getItem(name)
end

def getChange(name)
    $tileConf.getChange(name)
end

BLOCK_EFFECT_SPAWN_BOMBS = 1
BLOCK_EFFECT_FORCE_REGEN = 2
BLOCK_EFFECT_DESTROY_BLOCK = 3
BLOCK_EFFECT_SPAWN_BOMBS_NY = 4

ITEM_EFFECT_CHANGE_COUNT_TO_BOMBS_PLUS_TWO = 5
ITEM_EFFECT_TELEPORT_FROM_STRUCTURE = 6
ITEM_EFFECT_RANDOM_DESEASE = 7
ITEM_EFFECT_XOR = 8
ITEM_EFFECT_BALL = 9
ITEM_EFFECT_SANTA_CANT_TAKE = 10

next_ = getChange("next")
type = "type"
image = "image"
max = "max"
effect = "effect"
variants = "variants"
onBomb = "onBomb"
onDamage = "onDamage"
onAtomic = "onAtomic"
onPremiumDamage = "onPremiumDamage"
slot = "slot"
subground = "subground"
background = "background"
p = "p"
result = "result"
count = "count"
all = "all"
subTiles = "subTiles"
oriented = "oriented"
direction = "direction"
item = "item"
offset = "offset"

newSlot("kills", {type=> "stat", image=> 11})
newSlot("money", {type=> "stat", image=> 12})
newSlot("deaths", {type=> "hidden"})
newSlot("football_goals", {type=> "hidden"})
newSlot("medal", {type=> "hidden"})

newSlot("bomb", {image=> 0, "def"=>1, max=>20})
newSlot("power", {image=> 3, "def"=>2, max=>8})
newSlot("scate", {image=> 6, max=>4})
newSlot("kick", {image=> 7, max=>1})
newSlot("jelly", {image=> 2, max=>1})
newSlot("heart", {image=> 10, max=>1})
newSlot("detonator", {image=> 1, max=>15})
newSlot("bomb_super", {image=> 4, max=>3})
newSlot("bat", {image=> 8, max=>2})
newSlot("atomic", {image=> 5, max=>1})
newSlot("shield", {image=> 9, max=>1})

newItem("muffin", {image=> 16})

newItem("ball", {effect=> ITEM_EFFECT_BALL})
newItem("desease", {effect=> ITEM_EFFECT_RANDOM_DESEASE})
newItem("detonator_pack", {slot=> getSlot("detonator"), image=>1, effect=> ITEM_EFFECT_CHANGE_COUNT_TO_BOMBS_PLUS_TWO})

newItem("surprise", {image=> 11, effect=> ITEM_EFFECT_SANTA_CANT_TAKE, variants=> [
	{p=> 5, slot=> getSlot("bomb") },
	{p=> 5, slot=> getSlot("power") },
	{p=> 4, slot=> getSlot("scate") },
	{p=> 4, slot=> getSlot("kick") },
	{p=> 3, slot=> getSlot("jelly") },
	{p=> 5, slot=> getSlot("detonator") },
	{p=> 6, slot=> getItem("ball") },
	{p=> 0, slot=> getSlot("money"), count=>20}
]})

newItem("random", {image=> 12, effect=> ITEM_EFFECT_RANDOM_DESEASE, variants=> [
		{p=> 6 },
		{p=> 1, effect=> ITEM_EFFECT_BALL},
	{p=> 0, slot=> getSlot("money"), count=>20}
]})

newItem("ball_bonus", {all=>true,  variants=> [
	{count=>500, slot=> getSlot("money")},
	{count=>5, slot=> getSlot("bomb")},
	{count=>2, slot=> getSlot("power")},
	{count=>1, slot=> getSlot("scate")},
	{count=>1, slot=> getSlot("kick")},
	{count=>1, slot=> getSlot("shield")},
	{count=>1, slot=> getSlot("football_goals")}
]})

newItem("atomic_ball_bonus", {all=>true,  variants=> [
	{count=>500, slot=> getSlot("money")},
	{count=>10, slot=> getSlot("bomb")},
	{count=>4, slot=> getSlot("power")},
	{count=>2, slot=> getSlot("scate")},
	{count=>1, slot=> getSlot("kick")},
	{count=>1, slot=> getSlot("shield")},
	{count=>1, slot=> getSlot("bat")},
	{count=>1, slot=> getSlot("atomic")},
	{count=>1, slot=> getSlot("bomb_super")},
	{count=>1, slot=> getSlot("football_goals")}
]})

newItem("silver", {count=>10, slot=> getSlot("money"), image=> 14})
newItem("gold", {count=>20, slot=> getSlot("money"), image=> 13})
newItem("diamond", {count=>100, slot=> getSlot("money"), image=> 15})

defaultTile(getTile("grass"))
newGroup("grass", {
    type=> "floor",
    image=> 0,
	subground=> 19,
    onBomb=> newChange("",{variants=>[{p=>2}, {p=>1, result=> getTile("hole1")}]}),
    onAtomic=> getTile("dirty"),
    subTiles=> [
        sub("grass"),
        sub("grass2"),
        sub("grass3")
    ]
})
newTile("field", {
    type=> "floor",
    image=> 3,
    onAtomic=> getTile("dirty")
})
newGroup("hole", {
    type=> "floor",
    image=> 4,
    onBomb=> newChange("next_or_this", {
        variants=> [
            { p => 2 },
            { p => 1, result=> next_ }
        ]
    }),
    onAtomic=> getTile("dirty_hole"),
    subTiles=> [
        sub("hole1"),
        sub("hole2"),
        sub("hole3"),
        sub("hole4")
    ]
})
newGroup("rocky", {
    type=> "floor",
    image=> 8,
    onBomb=> getChange("next_or_this"),
    subTiles=> [
        sub("rocky", {onAtomic=> getTile("dirty")}),
        sub("rocky_hole", {onAtomic=> getTile("dirty_hole")})
    ]
})
newGroup("dirty", {
    type=> "floor",
    image=> 10,
    onBomb=> getChange("next_or_this"),
    subTiles=> [
        sub("dirty"),
        sub("dirty_hole")
    ]
})
newChange("destroy", {effect=> BLOCK_EFFECT_DESTROY_BLOCK, variants=> [
    {p=>2, result=> getTile("grass")},
    {p=>1, result=> getTile("grass2")},
    {p=>1, result=> getTile("grass3")}
]})
newChange("destroy_rocky", {effect=> BLOCK_EFFECT_DESTROY_BLOCK, variants=> [
    {p=>2, result=> getTile("grass")},
    {p=>1, result=> getTile("grass2")},
    {p=>1, result=> getTile("grass3")},
    {p=>1, result=> getTile("rocky")}
]})
newChange("destroy_dirty", {effect=> BLOCK_EFFECT_DESTROY_BLOCK, variants=> [
    {p=>2, result=> getTile("dirty")}
]})
newGroup("bridge", {
	type=> "floor",
	image=> 12,
	subground=> 67,
	subTiles=> [
		sub("bridge_h", {onBomb=> newChange("bridge_down",{variants=>[{p=>2}, {p=>1, result=> getTile("abyss"), effect=> BLOCK_EFFECT_DESTROY_BLOCK }]})}),
		sub("bridge_v", {onBomb=> getChange("bridge_down")}),
		sub("bridge_metal_h"),
		sub("bridge_metal_v")
	]
})
newGroup("tile", {
    type=> "floor",
    image=> 16,
    onBomb=> getChange("next_or_this"),
    onAtomic=> newChange("", {variants=>[
        {p=>1,result=>getTile("tile3")},
        {p=>1,result=>getTile("dirty")}
    ]}),
    subTiles=> [
        sub("tile1"),
        sub("tile2"),
        sub("tile3")
    ]
})
newTile("abyss", {
    type=> "abyss",
	image=> 1
})
newGroup("arrow", {
    type=> "arrow",
    oriented=> true,
    image=> 20,
    onAtomic=> getTile("dirty"),
    subTiles=> [
        sub("right", {direction=>0}),
        sub("up", {direction=>2}),
        sub("left", {direction=>4}),
        sub("down", {direction=>6})
    ]
})
newTile("brick", {
    type=> "solid",
    image=> 44,
    onDamage=> newChange("prise_in_brick", {
        "variants"=> [
                    { p => 60, item => getSlot("bomb")},
                    { p => 40, item => getSlot("power")},
                    { p => 40, item => getSlot("scate")},
                    { p => 10, item => getSlot("kick")}, 
                    { p => 10, item => getItem("random")},
                    { p => 10, item => getItem("surprise")},
                    { p => 10, item => getSlot("heart")},
					{ p => 430 }
                ],
        result=> getChange("destroy_rocky")
    }),
    onPremiumDamage=> newChange("prise_in_brick2", {
        "variants"=> [
                    { p => 60, item => getSlot("bomb")},
                    { p => 40, item => getSlot("power")},
                    { p => 40, item => getSlot("scate")},
                    { p => 10, item => getSlot("kick")}, 
                    { p => 10, item => getItem("random")},
                    { p => 10, item => getItem("surprise")},
                    { p => 1, item => getSlot("heart")},
					{ p => 230 }
                ],
        result=> getChange("destroy_rocky")
    })
})
newGroup("rock", {
    type=> "solid",
    image=> 24,
    onDamage=> next_,
    subTiles=> [
        sub("rock3", {
            onDamage=> newChange("next_or_chest", {
                variants=> [{p=> 9,result=> next_    }, {p=> 1, result=> getTile("chest_in_rock") }]
            })
        }),
        sub("rock2"),
        sub("rock1"),
        sub("rock0", {onDamage=> getChange("destroy")}),
        sub("silver3", {onDamage=> getChange("next_or_chest")}),
        sub("silver2"),
        sub("silver1"),
        sub("silver0", {onDamage=> newChange("", {item=> getItem("silver"), result=> getChange("destroy_dirty")})}),
        sub("gold3", {onDamage=> getChange("next_or_chest")}),
        sub("gold2"),
        sub("gold1"),
        sub("gold0", {onDamage=> newChange("", {item=>getItem("gold"), result=> getChange("destroy_dirty")})}),
        sub("diamond3", {onDamage=> getChange("next_or_chest")}),
        sub("diamond2"),
        sub("diamond1"),
        sub("diamond0", {onDamage=> newChange("", {item=>getItem("diamond"), result=> getChange("destroy_dirty")})})
    ]})
newGroup("tough", {
    type=> "solid",
    onDamage=> next_,
    image=> 40, 
    onAtomic=> getTile("dirty"),
    subTiles=> [
        sub("tough9", {onAtomic=> getTile("tough2")}),
        sub("tough8", {offset=>0, onAtomic=> getTile("tough1")}),
        sub("tough7", {offset=>0}),
        sub("tough6"),
        sub("tough5", {offset=>0}),
        sub("tough4"),
        sub("tough3", {offset=>0}),
        sub("tough2"),
        sub("tough1", {offset=>0, onDamage=> getChange("destroy_rocky")})
    ]
})
newGroup("wall", {
    type=> "solid",
    image=> 48,
    onDamage=> next_,
    onAtomic=> getTile("dirty"),
    subTiles=> [
        sub("wall6"),
        sub("wall5", {offset=>0}),
        sub("wall4"),
        sub("wall3", {offset=>0}),
        sub("wall2"),
        sub("wall1", {offset=>0, onDamage=> getChange("destroy_rocky")})
    ]
})
newTile("chest_in_rock", {
    image=> 52,
    type=> "solid",
    onDamage=> getTile("chest")
})
newTile("metal", {
    type=> "solid",
    image=> 53
})
newTile("tunnel", {
    type=> "tunnel",
    onAtomic=> getTile("rocky"),
    image=> 54
})
newTile("chest", {
    image=> 56,
    type=> "box",
    onDamage=> newChange("", {
        "effect"=> BLOCK_EFFECT_FORCE_REGEN,
        "result"=> getChange("destroy"),
        "variants"=> [
            { p => 3, item => getSlot("shield") }
        ]
    })
})
newTile("gold_chest", {
    image=> 57,
    type=> "box",
    onDamage=> newChange("", {
        "effect"=> BLOCK_EFFECT_FORCE_REGEN,
        "result"=> getChange("destroy"),
        "variants"=> [
            { p => 1, item=> newItem("", { slot=> getSlot("bomb_super"), effect=> ITEM_EFFECT_TELEPORT_FROM_STRUCTURE}) },
            { p => 3, item=> newItem("", { slot=> getSlot("bat"), effect=> ITEM_EFFECT_TELEPORT_FROM_STRUCTURE}) }
        ]
    })
})
newGroup("box", {
    type=> "box",
    image=> 58,
    subTiles=> [
        sub("box1", {onDamage=> newChange("", {
            item=> getSlot("power"), 
            effect=> BLOCK_EFFECT_DESTROY_BLOCK, 
            result=>getTile("tile1")})
        }),
        sub("box2", {
            offset=>0, 
            onDamage=> newChange("", {
				effect=> BLOCK_EFFECT_FORCE_REGEN,
				result=> getChange("destroy"),
				variants=> [
					{p=> 1, item=> getSlot("atomic")},
					{p=> 1, item=> getItem("detonator_pack")}
				]
			})
		})
    ]
})
newTile("box_with_bombs", {
    image=> 59,
    type=> "box",
    onDamage=> newChange("", {
        effect=>BLOCK_EFFECT_SPAWN_BOMBS, 
        result=>getChange("destroy_rocky")
    })
})
newTile("gate", {
    type=> "building",
    image=> 60,
    background=> 3
})
newTile("wc", {
    type=> "building",
    image=> 61
})
newGroup("bush", {
	image=> 64,
	subTiles=> [
		sub("bush_hollow", {type=>"hideout"} ),
		sub("bush", {type=>"solid", offset=> 0})
	]
})
