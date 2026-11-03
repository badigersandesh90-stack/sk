package com.example.data

object SampleData {
    fun getInitialProducts(): List<Product> = listOf(
        // Laptops & Desktops
        Product(
            id = 1,
            title = "HP 15s Intel Core i5 12th Gen Laptop",
            category = "Laptops & Desktops",
            condition = "Brand New",
            price = 51990.0,
            originalPrice = 59990.0,
            specifications = "• Intel Core i5-1235U (10 Cores, up to 4.4 GHz)\n• 16GB DDR4 3200MHz RAM\n• 512GB PCIe NVMe M.2 SSD\n• 15.6\" FHD (1920 x 1080) Micro-Edge Anti-Glare\n• Intel Iris Xe Graphics\n• Windows 11 Home + MS Office 2021\n• Weight: 1.69 kg | Fast Charge",
            stockStatus = "In Stock",
            warranty = "1 Year Brand Warranty",
            isFeatured = true
        ),
        Product(
            id = 2,
            title = "ASUS TUF Gaming A15 Ryzen 7 Laptop",
            category = "Laptops & Desktops",
            condition = "Brand New",
            price = 68990.0,
            originalPrice = 78990.0,
            specifications = "• AMD Ryzen 7 7435HS (8 Cores, 16 Threads)\n• NVIDIA GeForce RTX 3050 4GB GDDR6 (75W TGP)\n• 16GB DDR5 4800MHz RAM (Expandable up to 32GB)\n• 512GB PCIe 4.0 NVMe SSD\n• 15.6\" FHD 144Hz Adaptive-Sync Display\n• RGB Backlit Keyboard | MIL-STD-810H Military Toughness",
            stockStatus = "In Stock",
            warranty = "1 Year Brand Warranty",
            isFeatured = true
        ),
        Product(
            id = 3,
            title = "SK Custom Performance Gaming Desktop Tower",
            category = "Laptops & Desktops",
            condition = "Brand New",
            price = 58500.0,
            originalPrice = 67000.0,
            specifications = "• Intel Core i5-12400F 6-Core Processor\n• MSI B760M-E Motherboard\n• NVIDIA GeForce RTX 3060 12GB GDDR6\n• 16GB (8x2) Corsair Vengeance 3200MHz RAM\n• 1TB Crucial P3 Plus Gen4 NVMe SSD\n• DeepCool 650W 80+ Bronze SMPS\n• Ant Esports ICE-112 Tempered Glass ARGB Case",
            stockStatus = "In Stock",
            warranty = "3 Years Component Warranty",
            isFeatured = true
        ),

        // Refurbished & Second-Hand Laptops
        Product(
            id = 4,
            title = "Dell Latitude 7490 Core i5 Business Laptop",
            category = "Refurbished",
            condition = "Refurbished - Grade A",
            price = 21500.0,
            originalPrice = 85000.0,
            specifications = "• Intel Core i5-8350U Quad-Core Processor\n• 16GB DDR4 RAM (Dual Channel)\n• 256GB High-Speed M.2 SSD\n• 14.0\" Full HD IPS Anti-Glare Display\n• Magnesium Alloy Chassis | Backlit Keyboard\n• Original Dell 65W Fast Charger included\n• Fully Tested 32-Point Quality Check",
            stockStatus = "In Stock",
            warranty = "6 Months Shop Warranty + 1 Year Service",
            isFeatured = true
        ),
        Product(
            id = 5,
            title = "Lenovo ThinkPad T480 Dual-Battery Edition",
            category = "Refurbished",
            condition = "Refurbished - Grade A",
            price = 23900.0,
            originalPrice = 92000.0,
            specifications = "• Intel Core i5 8th Gen vPro Processor\n• 16GB DDR4 RAM\n• 512GB NVMe M.2 Solid State Drive\n• 14.0\" FHD Display\n• Legendary Spill-Resistant ThinkPad Keyboard\n• Dual Battery System (Internal + External Hot-Swappable)\n• USB Type-C Thunderbolt 3 & HDMI",
            stockStatus = "Limited Stock",
            warranty = "6 Months Shop Warranty",
            isFeatured = true
        ),
        Product(
            id = 6,
            title = "HP EliteBook 840 G6 Aluminum Slim Laptop",
            category = "Refurbished",
            condition = "Refurbished - Grade A",
            price = 26500.0,
            originalPrice = 98000.0,
            specifications = "• Intel Core i5-8265U Processor\n• 16GB DDR4 RAM\n• 512GB NVMe SSD\n• 14.0\" Full HD Ultra-Slim Display\n• Bang & Olufsen Premium Audio\n• All-Aluminum CNC Precision Finish\n• Fingerprint Reader & Privacy Camera Shutter",
            stockStatus = "In Stock",
            warranty = "6 Months Shop Warranty",
            isFeatured = false
        ),

        // Monitors & Printers
        Product(
            id = 7,
            title = "LG UltraGear 24\" 165Hz IPS Gaming Monitor",
            category = "Monitors & Printers",
            condition = "Brand New",
            price = 11499.0,
            originalPrice = 14500.0,
            specifications = "• 24-inch Full HD (1920x1080) IPS Panel\n• 165Hz Refresh Rate & 1ms MBR Response Time\n• AMD FreeSync Premium Support\n• HDR10 with 99% sRGB Color Gamut\n• 3-Side Virtually Borderless Design\n• DisplayPort 1.2 & HDMI 2.0 Ports",
            stockStatus = "In Stock",
            warranty = "3 Years Manufacturer Warranty",
            isFeatured = true
        ),
        Product(
            id = 8,
            title = "HP LaserJet Pro M126nw All-in-One WiFi Printer",
            category = "Monitors & Printers",
            condition = "Brand New",
            price = 18999.0,
            originalPrice = 22500.0,
            specifications = "• Print, Scan, Copy Multifunction Laser\n• Wireless Printing via WiFi, Ethernet & USB\n• High Speed: Up to 20 ppm (A4)\n• Ultra-low running cost with HP 88A toner cartridges\n• Mobile Printing via HP Smart App & Apple AirPrint\n• Ideal for Office & Commercial use",
            stockStatus = "In Stock",
            warranty = "1 Year Brand Warranty",
            isFeatured = false
        ),

        // Accessories (Keyboards, Mice, Audio)
        Product(
            id = 9,
            title = "Logitech G213 Prodigy RGB Gaming Keyboard",
            category = "Accessories",
            condition = "Brand New",
            price = 3795.0,
            originalPrice = 4995.0,
            specifications = "• Mech-Dome Tactile Responsive Keys\n• 5 Individual RGB Lighting Zones with LIGHTSYNC\n• Spill-Resistant & Durable Construction\n• Integrated Ergonomic Palm Rest\n• Dedicated Media Controls & Volume Wheel",
            stockStatus = "In Stock",
            warranty = "2 Years Replacement Warranty",
            isFeatured = false
        ),
        Product(
            id = 10,
            title = "Logitech G102 Lightsync Optical Gaming Mouse",
            category = "Accessories",
            condition = "Brand New",
            price = 1495.0,
            originalPrice = 1995.0,
            specifications = "• Gaming-Grade 8,000 DPI Sensor\n• Customizable RGB Color Wave Effects\n• Classic 6-Button Ergonomic Layout\n• Mechanical Button Tensioning with Metal Springs",
            stockStatus = "In Stock",
            warranty = "2 Years Warranty",
            isFeatured = false
        ),

        // Hardware Upgrades & Storage
        Product(
            id = 11,
            title = "Kingston NV2 1TB PCIe 4.0 NVMe M.2 SSD",
            category = "Hardware Upgrades",
            condition = "Brand New",
            price = 5600.0,
            originalPrice = 7200.0,
            specifications = "• Gen 4x4 NVMe PCIe Performance\n• Read Speeds up to 3,500 MB/s, Write up to 2,100 MB/s\n• Compact M.2 2280 Form Factor\n• Boosts Laptop & Desktop boot speeds by 10x\n• Free OS cloning and installation support at SK Computers",
            stockStatus = "In Stock",
            warranty = "3 Years Brand Warranty",
            isFeatured = true
        ),
        Product(
            id = 12,
            title = "Crucial 16GB DDR4 3200MHz Laptop/Desktop RAM",
            category = "Hardware Upgrades",
            condition = "Brand New",
            price = 3100.0,
            originalPrice = 4200.0,
            specifications = "• 16GB High Density DDR4 Module (3200MHz / PC4-25600)\n• 1.2V Low Power Consumption\n• Available in SODIMM (Laptop) and UDIMM (Desktop)\n• Instant multitasking upgrade & lag-free performance\n• Free on-spot installation and diagnostic testing",
            stockStatus = "In Stock",
            warranty = "10 Years Limited Warranty",
            isFeatured = false
        )
    )

    fun getDefaultStoreConfig(): StoreConfig = StoreConfig()
}
